package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Cell<T extends PDPage> {

	private float width;
	private Float height;
	private String text;

	private PDFont font = PDType1Font.HELVETICA;
	private PDFont fontBold = PDType1Font.HELVETICA_BOLD;

	private float fontSize = 8;
	private Color fillColor;
	private Color textColor = Color.BLACK;
	private final Row<T> row;
	private WrappingFunction wrappingFunction;
	private boolean isHeaderCell = false;
	private boolean isColspanCell = false;

	// default padding
	private float leftPadding = 5f;
	private float rightPadding = 5f;
	private float topPadding = 5f;
	private float bottomPadding = 5f;

	// default border
	private LineStyle leftBorderStyle = new LineStyle(Color.BLACK, 1);
	private LineStyle rightBorderStyle = new LineStyle(Color.BLACK, 1);
	private LineStyle topBorderStyle = new LineStyle(Color.BLACK, 1);
	private LineStyle bottomBorderStyle = new LineStyle(Color.BLACK, 1);

	private Paragraph paragraph = null;
	private float lineSpacing = 1;
	private boolean textRotated = false;

	private HorizontalAlignment align;
	private VerticalAlignment valign;

	float horizontalFreeSpace = 0;
	float verticalFreeSpace = 0;

	Cell(Row<T> row, float width, String text, boolean isCalculated) {
		this(row, width, text, isCalculated, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
	}

	Cell(Row<T> row, float width, String text, boolean isCalculated, HorizontalAlignment align,
			VerticalAlignment valign) {
		this.row = row;
		if (isCalculated) {
			double calclulatedWidth = ((row.getWidth() * width) / 100);
			this.width = (float) calclulatedWidth;
		} else {
			this.width = width;
		}

		if (getWidth() > row.getWidth()) {
			throw new IllegalArgumentException(
					"Cell Width=" + getWidth() + " can't be bigger than row width=" + row.getWidth());
		}
		//check if we have new default font
		if(!FontUtils.getDefaultfonts().isEmpty()){
			font = FontUtils.getDefaultfonts().get("font");
			fontBold = FontUtils.getDefaultfonts().get("fontBold");
		}
		this.text = text == null ? "" : text;
		this.align = align;
		this.valign = valign;
		this.wrappingFunction = null;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public float getWidth() {
		return width;
	}
	
	public float getInnerWidth() {
		return getWidth() - getLeftPadding() - getRightPadding()
				- (leftBorderStyle == null ? 0 : leftBorderStyle.getWidth())
				- (rightBorderStyle == null ? 0 : rightBorderStyle.getWidth());
	}

	public float getInnerHeight() {
		return getHeight() - getBottomPadding() - getTopPadding()
				- (topBorderStyle == null ? 0 : topBorderStyle.getWidth())
				- (bottomBorderStyle == null ? 0 : bottomBorderStyle.getWidth());
	}

	public String getText() {
		return text;
	}



	public PDFont getFont() {
		if (font == null) {
			throw new IllegalArgumentException("Font not set.");
		}
		if (isHeaderCell) {
			return fontBold;
		} else {
			return font;
		}
	}

	public void setFont(PDFont font) {
		this.font = font;

		// paragraph invalidated
		paragraph = null;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;

		// paragraph invalidated
		paragraph = null;
	}

	public Paragraph getParagraph() {
		if (paragraph == null) {
			// if it is header cell then use font bold
			if (isHeaderCell) {
				if (isTextRotated()) {
					paragraph = new Paragraph(text, fontBold, fontSize, getInnerHeight(), align, textColor, null,
							wrappingFunction, lineSpacing);
				} else {
					paragraph = new Paragraph(text, fontBold, fontSize, getInnerWidth(), align, textColor, null,
							wrappingFunction, lineSpacing);
				}
			} else {
				if (isTextRotated()) {
					paragraph = new Paragraph(text, font, fontSize, getInnerHeight(), align, textColor, null,
							wrappingFunction, lineSpacing);
				} else {
					paragraph = new Paragraph(text, font, fontSize, getInnerWidth(), align, textColor, null,
							wrappingFunction, lineSpacing);
				}
			}
		}
		return paragraph;
	}


	public float getHeight() {
		return row.getHeight();
	}


	public float getCellHeight() {
		if (height != null) {
			return height;
		}

		if (isTextRotated()) {
			try {
				return getFont().getStringWidth(getText()) / 1000 * getFontSize() + getTopPadding()
						+ (getTopBorder() == null ? 0 : getTopBorder().getWidth()) + getBottomPadding()
						+ (getBottomBorder() == null ? 0 : getBottomBorder().getWidth());
			} catch (final IOException e) {
				throw new IllegalStateException("Font not set.", e);
			}
		} else {
			return getTextHeight() + getTopPadding() + getBottomPadding()
					+ (getTopBorder() == null ? 0 : getTopBorder().getWidth())
					+ (getBottomBorder() == null ? 0 : getBottomBorder().getWidth());
		}
	}


	public float getTextHeight() {
		return getParagraph().getHeight();
	}


	public float getLeftPadding() {
		return leftPadding;
	}



	public float getRightPadding() {
		return rightPadding;
	}


	
	public float getTopPadding() {
		return topPadding;
	}


	
	public float getBottomPadding() {
		return bottomPadding;
	}

	public float getVerticalFreeSpace() {
		if (isTextRotated()) {
			// need to calculate max line width so we just iterating through
			// lines
			return getInnerHeight() - getParagraph().getMaxLineWidth();
		} else {
			return getInnerHeight() - getTextHeight();
		}
	}
	
	public float getHorizontalFreeSpace() {
		if (isTextRotated()) {
			return getInnerWidth() - getTextHeight();
		} else {
			return getInnerWidth() - getParagraph().getMaxLineWidth();
		}
	}

	public HorizontalAlignment getAlign() {
		return align;
	}

	public VerticalAlignment getValign() {
		return valign;
	}

	public boolean isHeaderCell() {
		return isHeaderCell;
	}

	public void setHeaderCell(boolean isHeaderCell) {
		this.isHeaderCell = isHeaderCell;
	}



	public LineStyle getLeftBorder() {
		return leftBorderStyle;
	}

	public LineStyle getRightBorder() {
		return rightBorderStyle;
	}

	public LineStyle getTopBorder() {
		return topBorderStyle;
	}

	public LineStyle getBottomBorder() {
		return bottomBorderStyle;
	}

	public void setLeftBorderStyle(LineStyle leftBorder) {
		this.leftBorderStyle = leftBorder;
	}

	public void setRightBorderStyle(LineStyle rightBorder) {
		this.rightBorderStyle = rightBorder;
	}

	public void setTopBorderStyle(LineStyle topBorder) {
		this.topBorderStyle = topBorder;
	}

	public void setBottomBorderStyle(LineStyle bottomBorder) {
		this.bottomBorderStyle = bottomBorder;
	}

	public void setBorderStyle(LineStyle border) {
		this.leftBorderStyle = border;
		this.rightBorderStyle = border;
		this.topBorderStyle = border;
		this.bottomBorderStyle = border;
	}

	public boolean isTextRotated() {
		return textRotated;
	}

	public void setTextRotated(boolean textRotated) {
		this.textRotated = textRotated;
	}

	public PDFont getFontBold() {
		return fontBold;
	}

	public void setAlign(HorizontalAlignment align) {
		this.align = align;
	}

	public void setValign(VerticalAlignment valign) {
		this.valign = valign;
	}



	public float getLineSpacing() {
		return lineSpacing;
	}

	public void setLineSpacing(float lineSpacing) {
		this.lineSpacing = lineSpacing;
	}

}

