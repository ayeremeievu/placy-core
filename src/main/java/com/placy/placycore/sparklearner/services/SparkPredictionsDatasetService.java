package com.placy.placycore.sparklearner.services;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.sparklearner.data.PredictionData;
import com.placy.placycore.sparklearner.mapper.RowToPredictionDataMapper;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

@Component
public class SparkPredictionsDatasetService {

    @Autowired
    private RowToPredictionDataMapper rowToPredictionDataMapper;

    public List<PredictionData> selectPredictionsByUserPk(Dataset<Row> dataset, UserModel userModel) {
        Dataset<Row> filteredDs = dataset.where(col("r_user_pk").equalTo(userModel.getPk()));

        List<Row> predictionsForUser = filteredDs
                .select("r_user_pk", "r_place_pk", "prediction").collectAsList();

        return predictionsForUser.stream()
                .map(row -> rowToPredictionDataMapper.map(row))
                .collect(Collectors.toList());
    }
}
