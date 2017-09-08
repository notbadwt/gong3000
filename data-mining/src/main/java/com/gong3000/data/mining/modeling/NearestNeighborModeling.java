package com.gong3000.data.mining.modeling;

import com.gong3000.data.mining.classifier.NearestNeighborClassifier;
import com.gong3000.data.mining.model.CharacteristicSpace;
import com.gong3000.data.mining.model.ClassType;
import com.gong3000.data.mining.model.Sample;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * 最邻近建模建模
 */
public class NearestNeighborModeling {

    /**
     * 建模结果
     */
    private CharacteristicSpace characteristicSpace;


    public CharacteristicSpace build() {
        try (InputStream stream = this.getClass().getResourceAsStream("/athletesTrainingSet.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            LinkedList<Sample> sampleList = new LinkedList<>();
            reader.lines().forEach(s -> {
                String[] strings = s.split("\t");
                Sample sample = new Sample();
                sample.setClassType(ClassType.getClassType(strings[1]));
                sample.setLabel(strings[0]);
                LinkedHashMap<String, Double> numbers = new LinkedHashMap<>();
                numbers.put("num1", Double.parseDouble(strings[2]));
                numbers.put("num2", Double.parseDouble(strings[3]));
                sample.setNumbers(numbers);
                sampleList.add(sample);
            });

            characteristicSpace = new CharacteristicSpace();
            characteristicSpace.setSampleList(sampleList);
            return characteristicSpace;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws Exception {
        NearestNeighborModeling nearestNeighborModeling = new NearestNeighborModeling();
        nearestNeighborModeling.build();
        NearestNeighborClassifier nearestNeighborClassifier = new NearestNeighborClassifier(nearestNeighborModeling.build());
        nearestNeighborClassifier.initModelData();
        nearestNeighborClassifier.normalization();
        Sample sample = new Sample();
        sample.setLabel("test");
        LinkedHashMap<String, Double> numbers = new LinkedHashMap<>();
        numbers.put("num1", 50d);
        numbers.put("num2", 179d);
        sample.setNumbers(numbers);
        List<Sample> nearestSample = nearestNeighborClassifier.nearestNeighbor(sample, 5);
        nearestSample.forEach(sample1 -> {
            System.out.println(sample1.getClassType().getName());
        });
    }


}
