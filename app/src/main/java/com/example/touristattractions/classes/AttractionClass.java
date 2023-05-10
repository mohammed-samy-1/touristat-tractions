package com.example.touristattractions.classes;

import java.util.ArrayList;

public class AttractionClass
{
    private String xid;
    private String name;
    Address address;
    private String rate;
    private String osm;
    Bbox BboxObject;
    private String wikidata;
    private String kinds;
    Sources SourcesObject;
    private String otm;
    private String wikipedia;
    private String image;
    Preview preview;
    Wikipedia_extracts wikipedia_extracts;
    Point PointObject;


    // Getter Methods

    public String getXid() {
        return xid;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getRate() {
        return rate;
    }

    public String getOsm() {
        return osm;
    }

    public Bbox getBbox() {
        return BboxObject;
    }

    public String getWikidata() {
        return wikidata;
    }

    public String getKinds() {
        return kinds;
    }

    public Sources getSources() {
        return SourcesObject;
    }

    public String getOtm() {
        return otm;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public String getImage() {
        return image;
    }

    public Preview getPreview() {
        return preview;
    }

    public Wikipedia_extracts getWikipedia_extracts() {
        return wikipedia_extracts;
    }

    public Point getPoint() {
        return PointObject;
    }

    // Setter Methods

    public void setXid(String xid) {
        this.xid = xid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address addressObject) {
        this.address = addressObject;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setOsm(String osm) {
        this.osm = osm;
    }

    public void setBbox(Bbox bboxObject) {
        this.BboxObject = bboxObject;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public void setSources(Sources sourcesObject) {
        this.SourcesObject = sourcesObject;
    }

    public void setOtm(String otm) {
        this.otm = otm;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPreview(Preview previewObject) {
        this.preview = previewObject;
    }

    public void setWikipedia_extracts(Wikipedia_extracts wikipedia_extractsObject) {
        this.wikipedia_extracts = wikipedia_extractsObject;
    }

    public void setPoint(Point pointObject) {
        this.PointObject = pointObject;
    }
    public class Point {
        private float lon;
        private float lat;


        // Getter Methods

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }

        // Setter Methods

        public void setLon(float lon) {
            this.lon = lon;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }
    }
    public class Wikipedia_extracts {
        private String title;
        private String text;
        private String html;


        // Getter Methods

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getHtml() {
            return html;
        }

        // Setter Methods

        public void setTitle(String title) {
            this.title = title;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setHtml(String html) {
            this.html = html;
        }
    }
    public class Preview {
        private String source;
        private float height;
        private float width;


        // Getter Methods

        public String getSource() {
            return source;
        }

        public float getHeight() {
            return height;
        }

        public float getWidth() {
            return width;
        }

        // Setter Methods

        public void setSource(String source) {
            this.source = source;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public void setWidth(float width) {
            this.width = width;
        }
    }
    public class Sources {
        private String geometry;
        ArrayList< Object > attributes = new ArrayList < Object > ();


        // Getter Methods

        public String getGeometry() {
            return geometry;
        }

        // Setter Methods

        public void setGeometry(String geometry) {
            this.geometry = geometry;
        }
    }
    public class Bbox {
        private float lon_min;
        private float lon_max;
        private float lat_min;
        private float lat_max;



        public void setLat_max(float lat_max) {
            this.lat_max = lat_max;
        }
    }
    public class Address {
        private String state;
        private String county;
        private String suburb;
        private String country;
        private String village;
        private String postcode;
        private String country_code;
        private String state_district;


        // Getter Methods

        public String getState() {
            return state;
        }

        public String getCounty() {
            return county;
        }

        public String getSuburb() {
            return suburb;
        }

        public String getCountry() {
            return country;
        }

        public String getVillage() {
            return village;
        }

        public String getPostcode() {
            return postcode;
        }

        public String getCountry_code() {
            return country_code;
        }

        public String getState_district() {
            return state_district;
        }

        // Setter Methods

        public void setState(String state) {
            this.state = state;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public void setSuburb(String suburb) {
            this.suburb = suburb;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public void setState_district(String state_district) {
            this.state_district = state_district;
        }
    }

}