import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.Random;

public class DecisionTree {
    public static void main(String[] args) {
        try {
            // Load the ARFF file
            DataSource source = new DataSource("survey_data.arff");
            Instances data = source.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);

            // Create the J48 decision tree classifier
            J48 tree = new J48();

            // Perform 10-fold cross-validation
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(tree, data, 2, new Random(1));

            // Print the results
            System.out.println(eval.toSummaryString("\nCross-Validation Results\n========================\n", false));

            // Train the classifier on the entire dataset
            tree.buildClassifier(data);

            // Print the built decision tree
            System.out.println(tree);

            // Save the model
            weka.core.SerializationHelper.write("j48model.model", tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
