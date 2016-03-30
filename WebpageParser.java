package com.stretchy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import com.stretchy.Gameboard;

/**
 * Created by ian on 3/19/16.
 */
public class WebpageParser
{
    private String WebURL;
    private Vector<String> Webpage;
    private String GameboardVars = null;
    private Gameboard innerGameboard;
    public WebpageParser(String url)
    {
        WebURL = url;
        ParsePage();
        if(GameboardVars == null)
        {
            System.out.println("No gameboard variable could be found\n");
        }
        else
        {
            CreateGameboard();
        }


    }

    public Gameboard GetBoard()
    {
        return innerGameboard;
    }

    private void CreateGameboard()
    {
        try{
            if(GameboardVars == null)
                throw new Exception("No Gameboard Variables found");
            else
            {
                String GameboardTerrain = null;
                String GameboardX = null;
                String GameboardY = null;
                String GameboardMaxI = null;
                String GameboardMinI = null;
                String GameboardL = null;

                LinkedList<String> StringTokens = new LinkedList<>();
                for(String token : GameboardVars.split("\""))
                {
                    StringTokens.add(token);
                    //System.out.println(token);
                }
                LinkedList<String> arguments = new LinkedList<>();
                for (String token : StringTokens.get(1).split("&"))
                {

                    arguments.add(token);
                }

                for (String element : arguments)
                {
                    if(element.startsWith("FVterrainString"))
                    {
                        GameboardTerrain = element.substring(element.indexOf("=")+1);
                    }
                    if(element.startsWith("FVinsMax"))
                    {
                        GameboardMaxI = element.substring(element.indexOf("=")+1);
                    }
                    if(element.startsWith("FVinsMin"))
                    {
                        GameboardMinI = element.substring(element.indexOf("=")+1);
                    }
                    if(element.startsWith("FVboardX"))
                    {
                        GameboardX = element.substring(element.indexOf("=")+1);
                    }
                    if(element.startsWith("FVboardY"))
                    {
                        GameboardY = element.substring(element.indexOf("=")+1);
                    }
                    if(element.startsWith("FVlevel"))
                    {
                        GameboardL = element.substring(element.indexOf("=")+1);
                    }

                }
                innerGameboard = new Gameboard(GameboardTerrain,
                                        Integer.parseInt(GameboardX),
                                        Integer.parseInt(GameboardY),
                                        Integer.parseInt(GameboardMaxI),
                                        Integer.parseInt(GameboardMinI),
                                        Integer.parseInt(GameboardL)
                                        );

            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void ParsePage()
    {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        try {
            url = new URL(WebURL);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                if(line.contains("FlashVars"))
                {
                    GameboardVars = line;
                    break;
                }
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

    }
}
