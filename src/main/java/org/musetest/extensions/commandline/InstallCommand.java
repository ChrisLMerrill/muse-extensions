package org.musetest.extensions.commandline;

import io.airlift.airline.*;
import org.musetest.core.commandline.*;
import org.musetest.extensions.*;
import org.musetest.extensions.install.*;
import org.musetest.extensions.registry.*;

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

            // update the extension registry
            final File project_folder = new File(System.getProperty("user.dir"));
            final File extreg_folder = new File(project_folder, ExtensionRegistry.DEFAULT_FOLDER);
            if (!extreg_folder.exists() && !extreg_folder.mkdirs())
                {
                System.out.println("Unable to create the extension registry folder: " + extreg_folder.getPath());
                return;
                }

            // install the extension
            System.out.println(String.format("Installing extension: %s...", info.getDisplayNameVersion()));
            ExtensionInstallLog log = ExtensionInstallers.find(info).install(info, project_folder);

            ExtensionRegistry registry = new ExtensionRegistry(extreg_folder);
            final ExtensionRegistryEntry entry = new ExtensionRegistryEntry();
            entry.setInfo(info);
            entry.setLog(log);
            registry.add(entry);

            System.out.println("done.");
            }
        catch (Exception e)
            {
            System.out.println("Failed to install extension: " + e.getLocalizedMessage());
            //e.printStackTrace();
            }

        }
    }


