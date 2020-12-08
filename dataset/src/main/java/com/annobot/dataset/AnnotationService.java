package com.annobot.dataset;

import com.annobot.dataset.model.DatasetItemAnnotation;
import com.annobot.dataset.model.IAA;

import java.util.List;

public interface AnnotationService {

    String getGoldLabel(List<DatasetItemAnnotation> annotations);

    IAA getIAA(List<DatasetItemAnnotation> annotations);
}
