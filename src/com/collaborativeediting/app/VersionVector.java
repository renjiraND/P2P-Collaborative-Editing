package com.collaborativeediting.app;

import java.util.ArrayList;

//    kelas ini memiliki fungsi untuk menyimpan version vector yang dimiliki oleh sebuah node (id-node dan counter operasi)
public class VersionVector {

    private ArrayList<Version> versions;
    private Version localVersion;

    public VersionVector(long siteId) {
        this.versions = new ArrayList<>();
        this.localVersion = new Version(siteId);
        this.versions.add(this.localVersion);
    }

    public void increment() {
        this.localVersion.increment();
    }

    public void update(Version incomingVersion) {
        final Version existingVersion = this.versions.get(this.versions.indexOf(incomingVersion));

        if (existingVersion != null){
            final Version newVersion = new Version(incomingVersion.getSiteId());

            newVersion.updateVersion(incomingVersion);
            this.versions.add(newVersion);
        } else {
          existingVersion.updateVersion(incomingVersion);
        }
    }

    public boolean hasBeenApplied(Version incomingVersion) {
        final Version localIncomingVersion = this.getVersionFromVector(incomingVersion);

        if (localIncomingVersion == null) return false;

        final boolean isLower = incomingVersion.getCounter() <= localIncomingVersion.getCounter();
        final boolean isInException = localIncomingVersion.getExceptions().contains(incomingVersion.getCounter());

        return  isLower && !isInException;
    }

    private Version getVersionFromVector(Version incomingVersion) {
        return this.versions.get(this.versions.indexOf(incomingVersion));
    }

    public Version getLocalVersion() {
        Version local = new Version(this.localVersion.getSiteId());
        local.setCounter(this.localVersion.getCounter());
        return local;
    }

}
