package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class GuiEpixTransparentButton extends GuiButton {
	
	private int color = 0xfffff;

    public GuiEpixTransparentButton(int par1, int par2, int par3, String par4Str)
    {
        this(par1, par2, par3, 160, 20, par4Str, 0xfffff);
    }

	public GuiEpixTransparentButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
    {
    	this(par1, par2, par3, par4, par5, par6Str, 0xffffff);
    }
	
	public GuiEpixTransparentButton(int par1, int par2, int par3, int par4, int par5, String par6Str, int par7)
    {
    	super(par1, par2, par3, par4, par5, par6Str);
        this.width = 160;
        this.height = 20;
        this.enabled = true;
        this.drawButton = true;
        this.id = par1;
        this.xPosition = par2;
        this.yPosition = par3;
        this.width = par4;
        this.height = par5;
        this.displayString = par6Str;
        this.color = par7;
    }
	
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            FontRenderer var4 = par1Minecraft.fontRenderer;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/epix/button.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 236, this.width, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int var6 = 16777215;
            var6 = this.color;
            
            if (!this.enabled)
            {
                var6 = -6250336;
            }
            else if (this.field_82253_i)
            {
                var6 = 16777120;
            }

            this.drawString(var4, this.displayString, this.xPosition, this.yPosition + (this.height - 8) / 2, var6);
        }
    }

}
