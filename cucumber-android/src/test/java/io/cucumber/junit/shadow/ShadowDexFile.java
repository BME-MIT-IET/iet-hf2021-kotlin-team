package io.cucumber.junit.shadow;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import dalvik.system.DexFile;

@Implements(DexFile.class)
public class ShadowDexFile {

    private Enumeration<String> entries;

    @Implementation
    public Enumeration<String> entries() {
        return entries;
    }

    public void setEntries(final Collection<String> entries) {
        this.entries = Collections.enumeration(entries);
    }
}
