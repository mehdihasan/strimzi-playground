package com.test.kafka.producer.model;

import com.test.kafka.producer.enums.Color;
import com.test.kafka.producer.enums.ProductType;
import com.test.kafka.producer.enums.DesignType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InternalProduct {

    private Color color;

    private ProductType type;

    private DesignType designType;

}
