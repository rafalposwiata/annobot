package com.annobot.dataset;

import com.annobot.dataset.model.DatasetItemAnnotation;
import com.annobot.dataset.model.IAA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.compare;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.*;

public class AnnotationServiceImpl implements AnnotationService {

    @Override
    public String getGoldLabel(List<DatasetItemAnnotation> annotations) {
        if (annotations.isEmpty()) return null;
        List<String> labelsWithMostOccurrences = labelsWithMostOccurrences(annotations);

        if (labelsWithMostOccurrences.size() == 1) {
            return labelsWithMostOccurrences.get(0);
        } else {
            return "Ambiguous";
        }
    }

    @Override
    public IAA getIAA(List<DatasetItemAnnotation> annotations) {
        if (annotations.isEmpty()) return new IAA(true);

        Map<Long, List<String>> itemsWithAnnotations = new HashMap<>();
        annotations.forEach(annotation -> {
            if (!itemsWithAnnotations.containsKey(annotation.getDatasetItemId())) {
                itemsWithAnnotations.put(annotation.getDatasetItemId(), new ArrayList<>());
            }
            itemsWithAnnotations.get(annotation.getDatasetItemId()).add(annotation.getAnnotation());
        });

        Double iaa = itemsWithAnnotations
                .values()
                .stream()
                .map(this::agreement)
                .mapToDouble(e -> e)
                .average()
                .orElse(0);

        return new IAA(iaa);
    }

    private Double agreement(List<String> annotations) {
        if (annotations.size() <= 1) return 1.0;

        int agg = 0;
        double all = 0;
        for (int i = 0; i < annotations.size(); i++) {
            for (int j = 0; j < annotations.size(); j++) {
                if (i != j) {
                    String a1 = annotations.get(i);
                    String a2 = annotations.get(j);
                    if (a1.equals(a2)) {
                        agg++;
                    }
                    all++;
                }
            }
        }
        return agg / all;
    }

    private List<String> labelsWithMostOccurrences(List<DatasetItemAnnotation> annotations) {
        List<AnnotationOccurrence> labelsWithCounts = labelsWithCounts(annotations);
        Long maxOccurrences = labelsWithCounts.get(0).occurrences;
        return labelsWithCounts
                .stream()
                .filter(e -> e.getOccurrences().equals(maxOccurrences))
                .map(AnnotationOccurrence::getAnnotation)
                .collect(toList());
    }

    private List<AnnotationOccurrence> labelsWithCounts(List<DatasetItemAnnotation> annotations) {
        return annotations
                .stream()
                .collect(groupingBy(DatasetItemAnnotation::getAnnotation, counting()))
                .entrySet()
                .stream()
                .map(entry -> new AnnotationOccurrence(entry.getKey(), entry.getValue()))
                .sorted(reverseOrder())
                .collect(toList());
    }

    private static class AnnotationOccurrence implements Comparable<AnnotationOccurrence> {
        private final String annotation;
        private final Long occurrences;

        public AnnotationOccurrence(String annotation, Long occurrences) {
            this.annotation = annotation;
            this.occurrences = occurrences;
        }

        public String getAnnotation() {
            return annotation;
        }

        public Long getOccurrences() {
            return occurrences;
        }

        @Override
        public int compareTo(AnnotationOccurrence other) {
            return compare(occurrences, other.occurrences);
        }
    }
}
