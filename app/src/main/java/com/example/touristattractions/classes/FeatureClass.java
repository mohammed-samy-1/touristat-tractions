package com.example.touristattractions.classes;

import java.util.ArrayList;

public class FeatureClass
{
    int anIntId;

    public int getAnIntId()
    {
        return anIntId;
    }

    public void setAnIntId(int anIntId)
    {
        this.anIntId = anIntId;
    }

    String type;
     String id;
    Geometry GeometryObject;
    Properties properties;


    // Getter Methods

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Geometry getGeometry() {
        return GeometryObject;
    }

    public Properties getProperties() {
        if (properties == null)
        {
            properties = new Properties();
        }
        return properties;
    }

    // Setter Methods

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGeometry(Geometry geometryObject) {
        this.GeometryObject = geometryObject;
    }

    public void setProperties(Properties propertiesObject) {
        this.properties = propertiesObject;
    }
    public class Properties {
         String xid;
         String name;
         float rate;
         String osm;
         String wikidata;
         String kinds;


        // Getter Methods

        public String getXid() {
            return xid;
        }

        public String getName() {
            return name;
        }

        public float getRate() {
            return rate;
        }

        public String getOsm() {
            return osm;
        }

        public String getWikidata() {
            return wikidata;
        }

        public String getKinds() {
            return kinds;
        }

        // Setter Methods

        public void setXid(String xid) {
            this.xid = xid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

        public void setOsm(String osm) {
            this.osm = osm;
        }

        public void setWikidata(String wikidata) {
            this.wikidata = wikidata;
        }

        public void setKinds(String kinds) {
            this.kinds = kinds;
        }
    }
    public class Geometry {
         String type;
        ArrayList< Object > coordinates = new ArrayList < Object > ();


        // Getter Methods

        public String getType() {
            return type;
        }

        // Setter Methods

        public void setType(String type) {
            this.type = type;
        }
    }
}