package org.museautomation.extensions.commandline;

import io.airlift.airline.*;
import org.museautomation.extensions.install.*;
import org.museautomation.extensions.registry.*;
import org.museautomation.core.commandline.*;
import org.museautomation.extensions.api.*;

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
    @SuppressWarnings("WeakerAccess")
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

            // install the extension
            System.out.println(String.format("Installing extension: %s...", info.getDisplayNameVersion()));
            ExtensionRegistry registry = new ExtensionRegistry(extreg_folder);
            ExtensionInstallLog log = new ExtensionInstallLog(project_folder);
            ExtensionInstallers.find(info).install(info, project_folder, registry, log);

            System.out.println(String.format("%d assets installed successfully.", log.getNumberFilesInstalled()));
            if (log.getNumberActionFailures() > 0)
                System.out.println(String.format("%d install actions failed.", log.getNumberActionFailures()));
            if (log.isRegistryUpdated())
                System.out.println(String.format("%s added to the extension registry.", info.getDisplayNameVersion()));
            else if (log.getRegistryUpdateMessage() != null)
                System.out.println("The extension registry could not be updated due to: " + log.getRegistryUpdateMessage());

            System.out.println("done.");
            }
        catch (Exception e)
            {
            System.out.println("Failed to install extension: " + e.getLocalizedMessage());
            //e.printStackTrace();
            }

        }
    }


