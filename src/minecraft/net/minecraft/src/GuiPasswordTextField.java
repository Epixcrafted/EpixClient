package net.minecraft.src;

public class GuiPasswordTextField extends GuiTextField {
	
	private boolean showText = false;

	public GuiPasswordTextField(FontRenderer par1FontRenderer, int par2,
			int par3, int par4, int par5) {
		super(par1FontRenderer, par2, par3, par4, par5);
		showText = true;
	}
	
    /**
     * Draws the textbox
     */
	@Override
    public void drawTextBox()
    {
        if (this.getVisible())
        {
            if (this.getEnableBackgroundDrawing())
            {
                drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
                drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
            }

            int var1 = this.isEnabled ? this.enabledColor : this.disabledColor;
            int var2 = this.cursorPosition - this.field_73816_n;
            int var3 = this.selectionEnd - this.field_73816_n;
            String var4 = this.fontRenderer.trimStringToWidth(this.text.substring(this.field_73816_n), this.getWidth());
            boolean var5 = var2 >= 0 && var2 <= var4.length();
            boolean var6 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && var5;
            int var7 = this.enableBackgroundDrawing ? this.xPos + 4 : this.xPos;
            int var8 = this.enableBackgroundDrawing ? this.yPos + (this.height - 8) / 2 : this.yPos;
            int var9 = var7;

            if (var3 > var4.length())
            {
                var3 = var4.length();
            }
            
            if (showText) {
                int length = var4.length();
                var4 = "";
                for (int i = 0; i < length; i++) {
                	var4 += "*";
                }
            }

            if (var4.length() > 0)
            {
                String var10 = var5 ? var4.substring(0, var2) : var4;
                var9 = this.fontRenderer.drawStringWithShadow(var10, var7, var8, var1);
            }

            boolean var13 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int var11 = var9;

            if (!var5)
            {
                var11 = var2 > 0 ? var7 + this.width : var7;
            }
            else if (var13)
            {
                var11 = var9 - 1;
                --var9;
            }

            if (var4.length() > 0 && var5 && var2 < var4.length())
            {
                this.fontRenderer.drawStringWithShadow(var4.substring(var2), var9, var8, var1);
            }

            if (var6)
            {
                if (var13)
                {
                    Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
                }
                else
                {
                    this.fontRenderer.drawStringWithShadow("_", var11, var8, var1);
                }
            }

            if (var3 != var2)
            {
                int var12 = var7 + this.fontRenderer.getStringWidth(var4.substring(0, var3));
                this.drawCursorVertical(var11, var8 - 1, var12 - 1, var8 + 1 + this.fontRenderer.FONT_HEIGHT);
            }
        }
    }

	public boolean toggleShowingText() {
		return (showText = !showText);
	}

}
