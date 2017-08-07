package org.musetest.extensions.commandline;

import io.airlift.airline.*;
import org.musetest.core.commandline.*;
import org.musetest.extensions.*;
import org.musetest.extensions.install.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@Command(name = "install-extension", description = "Install an extension into the project")
public class InstallCommand extends MuseCommand
    {
    @Arguments(description = "id of the extension version to install", required = true)
    public String extension_id;

    @Override
    public void run()
        {
        try
            {
            System.out.println(String.format("Fetching extension information (%s)...", extension_id));
            ExtensionInfo info = null;
            Client client = null;
            try
                {
                client = ClientBuilder.newClient();
                WebTarget target = client.target("http://localhost/ws/extension/version/" + extension_id);
                Response response = target.request(MediaType.APPLICATION_JSON).get();
                if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode())
                    System.out.println(String.format("The extension (%s) was not found.", extension_id));
                else
                    info = response.readEntity(ExtensionInfo.class);
                }
            catch (Exception e)
                {
                System.out.println("Unable to fetch the extension information: " + e.getLocalizedMessage());
                }
            finally
                {
                if (client != null)
                    client.close();
                }

            if (info == null)
                return;

            System.out.println(String.format("Installing extension: %s...", info.getDisplayNameVersion()));
            ExtensionInstallers.find(info).install(info, new File(System.getProperty("user.dir")));
            System.out.println("done.");
            }
        catch (Exception e)
            {
            System.out.println("Failed to install extension: " + e.getLocalizedMessage());
            //e.printStackTrace();
            }

        }
    }


