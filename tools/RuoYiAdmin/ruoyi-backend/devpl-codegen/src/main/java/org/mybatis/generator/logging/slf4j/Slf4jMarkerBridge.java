package org.mybatis.generator.logging.slf4j;

import org.slf4j.Marker;

import java.util.Iterator;

class Slf4jMarkerBridge implements Marker {

    private final org.mybatis.generator.logging.Marker marker;

    public Slf4jMarkerBridge(org.mybatis.generator.logging.Marker marker) {
        this.marker = marker;
    }

    @Override
    public String getName() {
        return marker.getName();
    }

    @Override
    public void add(Marker reference) {

    }

    @Override
    public boolean remove(Marker reference) {
        return false;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public boolean hasReferences() {
        return false;
    }

    @Override
    public Iterator<Marker> iterator() {
        return null;
    }

    @Override
    public boolean contains(Marker other) {
        return false;
    }

    @Override
    public boolean contains(String name) {
        return marker.contains(name);
    }
}
