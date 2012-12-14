package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class GuiEpixAuth extends GuiScreen {
	
	private GuiScreen lastScreen;
	private Minecraft mc;
    private GuiTextField login;
    private GuiPasswordTextField pass;

	public GuiEpixAuth(Minecraft mc, GuiScreen lastScreen) {
		this.lastScreen = lastScreen;
		this.mc = mc;
	}
	
    public void updateScreen()
    {
        login.updateCursorCounter();
        pass.updateCursorCounter();
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 90, height / 4 + 51 + 12, 180, 20, stringtranslate.translateKey("epix.auth.logIn")));
        controlList.add(new GuiButton(1, width / 2 - 90, height / 4 + 75 + 12, 85, 20, stringtranslate.translateKey("epix.auth.forgotPass")));
        controlList.add(new GuiButton(2, width / 2 + 6, height / 4 + 75 + 12, 85, 20, stringtranslate.translateKey("epix.auth.skip")));
        controlList.add(new GuiEpixTransparentButton(3, width / 2 + 90 - fontRenderer.getStringWidth(stringtranslate.translateKey("epix.auth.showPass")), ((height - 224 + 135)/ 2) - 2, 50, 15, stringtranslate.translateKey("epix.auth.showPass"), 0xADFFE7));
        login = new GuiTextField(fontRenderer, ((width-192+12) / 2), ((height - 224 + 75)/ 2), 180, 20);
        login.setFocused(true);
        pass = new GuiPasswordTextField(fontRenderer, ((width-192+12) / 2), ((height - 224 + 160)/ 2), 180, 20);
        pass.setMaxStringLength(64);
        operateAccountFile(0);
        ((GuiButton)controlList.get(0)).enabled = false;
    }
    
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }
        if (par1GuiButton.id == 0) logIn(login.getText(), pass.getText());
        if (par1GuiButton.id == 1)  Browser.openURL("http://vk.com/brilzlian");
        if (par1GuiButton.id == 2) this.mc.displayGuiScreen(lastScreen);
        if (par1GuiButton.id == 3) pass.toggleShowingText();
    }
    
    protected void keyTyped(char par1, int par2)
    {
        login.textboxKeyTyped(par1, par2);
        pass.textboxKeyTyped(par1, par2);

        if (par1 == '\t')
        {
        	login.setFocused(!login.isFocused());
        	pass.setFocused(!pass.isFocused());
        }
        
        if (par1 == '\r')
        {
        	if (login.getText().length() > 0 && pass.getText().length() > 0)
            {
        		if (pass.isFocused())
        		{
        			logIn(login.getText(), pass.getText());
        		}
            }
        	login.setFocused(!login.isFocused());
        	pass.setFocused(!pass.isFocused());
        }
            if (login.getText().length() > 0 && pass.getText().length() > 0)
            {
                ((GuiButton)controlList.get(0)).enabled = true;
            }else{
            	((GuiButton)controlList.get(0)).enabled = false;
            }
    }
    
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        login.mouseClicked(par1, par2, par3);
        pass.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
    	drawDefaultBackground();
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("epix.auth.title"), width/ 2, ((height - 224 + 20)/ 2), 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("epix.auth.username"), ((width-192+10) / 2), ((height - 224 + 50)/ 2), 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("epix.auth.password"), ((width-192+10) / 2), ((height - 224 + 135)/ 2), 0xffffff);
        login.drawTextBox();
        pass.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    public void drawDefaultBackground()
    {
        int var1 = this.mc.renderEngine.getTexture("/gui/epix/bg_login.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var1);
        this.drawTexturedModalRect((this.width-192) / 2, (this.height-224) / 2, 0, 0, 192, 224);
    }
    
    public void operateAccountFile(int type) {
    	switch (type) {
    	    case 0:
    	        try {
    	            File file = new File("bin/username");
    	            Scanner scanner = new Scanner(file);
    	            if (scanner.hasNext()) {
    	            	login.setText(scanner.next());
    	            }
    	            scanner.close();
    	          } catch (FileNotFoundException e) {
    	            e.printStackTrace();
    	          }
    	    case 1:
	    		try {
    	        	FileWriter fw;
	    			fw = new FileWriter("bin/username");
	    			fw.write(login.getText());
	    			fw.close();
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
    	}
    }
    
    public void logIn(String username, String password) {
    	this.mc.session.username = username;
    	this.mc.session.password = password;
    	operateAccountFile(1);
    	this.mc.displayGuiScreen(lastScreen);
    }
    
    static class Browser {
    	 
    	   static final String[] browsers = { "firefox", "opera", "konqueror", "epiphany",
    	      "seamonkey", "galeon", "kazehakase", "mozilla", "netscape", "chromium", "chrome" };
    	 
    	   public static void openURL(String url) {
    	      String osName = System.getProperty("os.name");
    	      try {
    	         if (osName.startsWith("Mac OS")) {
    	            Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
    	            Method openURL = fileMgr.getDeclaredMethod("openURL",
    	               new Class[] {String.class});
    	            openURL.invoke(null, new Object[] {url});
    	            }
    	         else if (osName.startsWith("Windows"))
    	            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
    	         else {
    	            boolean found = false;
    	            for (String browser : browsers)
    	               if (!found) {
    	                  found = Runtime.getRuntime().exec(
    	                     new String[] {"which", browser}).waitFor() == 0;
    	                  if (found)
    	                     Runtime.getRuntime().exec(new String[] {browser, url});
    	                  }
    	            if (!found)
    	               throw new Exception();
    	            }
    	         }
    	      catch (Exception e) {
    	         }
    	      }
    	 
    	   }
}
