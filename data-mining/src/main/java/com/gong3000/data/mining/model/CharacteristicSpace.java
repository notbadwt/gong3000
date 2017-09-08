package com.gong3000.data.mining.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 特征空间
 */
public class CharacteristicSpace {

    /**
     * 样本集合
     */
    private LinkedList<Sample> sampleList;


    public LinkedList<Sample> getSampleList() {
        return sampleList;
    }

    public void setSampleList(LinkedList<Sample> sampleList) {
        this.sampleList = sampleList;
    }
}
