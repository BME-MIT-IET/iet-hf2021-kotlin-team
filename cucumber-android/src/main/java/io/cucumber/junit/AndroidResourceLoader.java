package io.cucumber.junit;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cucumber.runtime.CucumberException;
import cucumber.runtime.io.Resource;
import cucumber.runtime.io.ResourceLoader;

/**
 * Android specific implementation of {@link cucumber.runtime.io.ResourceLoader} which loads non-class resources such as .feature files.
 */
final class AndroidResourceLoader implements ResourceLoader {

    /**
     * The format of the resource path.
     */
    private static final String RESOURCE_PATH_FORMAT = "%s/%s";

    /**
     * The {@link Context} to get the resources from.
     */
    private final Context context;

    /**
     * Creates a new instance for the given parameter.
     *
     * @param context the {@link Context} to get resources from
     */
    AndroidResourceLoader(final Context context) {
        this.context = context;
    }

    @Override
    public Iterable<Resource> resources(final URI path, final String suffix) {
        try {
            final List<Resource> resources = new ArrayList<>();
            final AssetManager assetManager = context.getAssets();
            addResourceRecursive(resources, assetManager, path, suffix);
            return resources;
        } catch (final IOException e) {
            throw new CucumberException("Error loading resources from " + path + " with suffix " + suffix, e);
        }
    }

    private void addResourceRecursive(final List<Resource> resources,
                                      final AssetManager assetManager,
                                      final URI path,
                                      final String suffix) throws IOException {
        String schemeSpecificPart = path.getSchemeSpecificPart();
        if (schemeSpecificPart.endsWith(suffix)) {
            resources.add(new AndroidResource(context, path));
            return;
        }

        String[] list = assetManager.list(schemeSpecificPart);
        if (list != null) {
            for (String name : list) {
                String subPath = String.format(RESOURCE_PATH_FORMAT, path.toString(), name);
                addResourceRecursive(resources, assetManager, URI.create(subPath), suffix);
            }
        }
    }
}
