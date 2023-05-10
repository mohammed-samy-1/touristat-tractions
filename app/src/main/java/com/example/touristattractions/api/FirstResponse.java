package com.example.touristattractions.api;

import com.example.touristattractions.classes.FeatureClass;

import java.util.List;

public class FirstResponse
{
    private List<FeatureClass> features;

    public List<FeatureClass> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureClass> features) {
        this.features = features;
    }
}

