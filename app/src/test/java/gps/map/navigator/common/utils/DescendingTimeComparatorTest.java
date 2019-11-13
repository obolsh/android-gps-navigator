package gps.map.navigator.common.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gps.map.navigator.model.interfaces.IMapPlace;

import static org.junit.Assert.assertEquals;

public class DescendingTimeComparatorTest {

    @Test
    public void make_sort_verify() {
        List<IMapPlace> places = getPlaces();

        Collections.sort(places, new DescendingTimeComparator());

        assertEquals(SecondPlace.class.getName(), places.get(0).getId());
        assertEquals(FirstPlace.class.getName(), places.get(1).getId());
    }

    private List<IMapPlace> getPlaces() {
        List<IMapPlace> places = new ArrayList<>();
        places.add(new FirstPlace());
        places.add(new SecondPlace());
        return places;
    }

    private static class FirstPlace implements IMapPlace {

        @Override
        public String getId() {
            return FirstPlace.class.getName();
        }

        @Override
        public void setId(String id) {

        }

        public long getLongitude() {
            return 0;
        }

        public void setLongitude(long longitude) {

        }

        public long getLatitude() {
            return 0;
        }

        public void setLatitude(long y) {

        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public void setTitle(String title) {

        }

        @Override
        public String getAddress() {
            return null;
        }

        @Override
        public void setAddress(String address) {

        }

        @Override
        public String getLabel() {
            return null;
        }

        @Override
        public void setLabel(String label) {

        }

        @Override
        public boolean isFavourite() {
            return false;
        }

        @Override
        public void setFavourite(boolean favourite) {

        }

        @Override
        public long getLastUsedTime() {
            return 123L;
        }

        @Override
        public void setLastUsedTime(long lastUsedTime) {

        }
    }

    private static class SecondPlace implements IMapPlace {

        @Override
        public String getId() {
            return SecondPlace.class.getName();
        }

        @Override
        public void setId(String id) {

        }

        public long getLongitude() {
            return 0;
        }

        public void setLongitude(long longitude) {

        }

        public long getLatitude() {
            return 0;
        }

        public void setLatitude(long y) {

        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public void setTitle(String title) {

        }

        @Override
        public String getAddress() {
            return null;
        }

        @Override
        public void setAddress(String address) {

        }

        @Override
        public String getLabel() {
            return null;
        }

        @Override
        public void setLabel(String label) {

        }

        @Override
        public boolean isFavourite() {
            return false;
        }

        @Override
        public void setFavourite(boolean favourite) {

        }

        @Override
        public long getLastUsedTime() {
            return 123L + 1L;
        }

        @Override
        public void setLastUsedTime(long lastUsedTime) {

        }
    }
}