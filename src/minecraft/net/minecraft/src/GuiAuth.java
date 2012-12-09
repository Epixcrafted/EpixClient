package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

public class GuiAuth extends GuiScreen
{

    private GuiTextField loginF;
    private GuiTextField passF;
    private Minecraft mc;
    private ServerData server;
    
    public GuiAuth(Minecraft mc, ServerData server)
    {
    	this.mc = mc;
    	this.server = server;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        loginF.updateCursorCounter();
        passF.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("Войти")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("Отмена")));
        loginF = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
        loginF.setFocused(true); //focus

        try {
            File file = new File("bin/username");
            Scanner scanner = new Scanner(file);
            if (scanner.hasNext()) {
            	loginF.setText(scanner.next());
            }
            scanner.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        //Add get text from file
        passF = new GuiTextField(fontRenderer, width / 2 - 100, 116, 200, 20);
        passF.setMaxStringLength(128);
        ((GuiButton)controlList.get(0)).enabled = false;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }

        if (par1GuiButton.id == 1)
        {
        	this.mc.displayGuiScreen(new GuiMultiplayer(this.mc.currentScreen));
        }
        else if (par1GuiButton.id == 0)
        {
        	auth(loginF.getText(), passF.getText());
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        loginF.textboxKeyTyped(par1, par2);//enter key
        passF.textboxKeyTyped(par1, par2);

        if (par1 == '\t')
        {
            if (loginF.isFocused())//isFocused
            {
                loginF.setFocused(false);//focus
                passF.setFocused(true);
            }
            else
            {
                loginF.setFocused(true);
                passF.setFocused(false);
            }
        }
        
        if (par1 == '\r')
        {
        	if (loginF.getText().length() > 0 && passF.getText().length() > 0)
            {
        		if (passF.isFocused())//isFocused
        		{
        			auth(loginF.getText(), passF.getText());
        		}
            }
        }

            if (loginF.getText().length() > 0 && passF.getText().length() > 0)
            {
                ((GuiButton)controlList.get(0)).enabled = true;
            }else{
            	((GuiButton)controlList.get(0)).enabled = false;
            }
            
        
    }
    
    private void auth(String login, String pass) {
    	FileWriter fw;
		try {
			fw = new FileWriter("bin/username");
			fw.write(loginF.getText());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mc.displayGuiScreen(new GuiConnecting(this.mc, this.server, login, pass));
    }
	/**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        loginF.mouseClicked(par1, par2, par3);
        passF.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, stringtranslate.translateKey("Авторизация"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("Логин:"), width / 2 - 100, 63, 0xa0a0a0);
        drawString(fontRenderer, stringtranslate.translateKey("Пароль:"), width / 2 - 100, 104, 0xa0a0a0);
        loginF.drawTextBox();
        passF.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}