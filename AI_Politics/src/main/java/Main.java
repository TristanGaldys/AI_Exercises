import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.SerializationHelper;

public class Main {

    // Inner class representing a survey question with its text and options
    private static class Question {
        String questionText;
        String[] options;

        Question(String questionText, String[] options) {
            this.questionText = questionText;
            this.options = options;
        }
    }


    // GUI components and data structures to hold the survey questions and answers
    private static JPanel panel;
    private static JLabel questionLabel;
    private static ButtonGroup optionGroup;
    private static JButton nextButton;
    private static JButton prevButton;
    private static J48 tree;
    private static Instances data;

    /**
     * The entry point of the application.
     * It initializes the Weka data structures and sets up the GUI.
     */
    public static void main(String[] args) {
        try {
            // Load the ARFF file
            DataSource source = new DataSource("survey_data.arff");
            data = source.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);
    
            // Load the trained J48 decision tree model
            tree = (J48) SerializationHelper.read("j48model.model");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Political Affiliation Survey");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Create a panel to hold all other components
            panel = new JPanel();
            frame.add(panel);

            // Initialize components
            questionLabel = new JLabel();
            optionGroup = new ButtonGroup();
            nextButton = new JButton("Next");
            prevButton = new JButton("Previous");

            // Add action listeners to the buttons
            nextButton.addActionListener(e -> handleNextButton());
            prevButton.addActionListener(e -> handlePrevButton());

            // Set layout manager
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Call a method to start the survey
            startSurvey();

            // Set the frame visibility to true to display the GUI
            frame.setVisible(true);
        });
    }


    // List to hold the survey questions and answers
    private static List<Question> questions = new ArrayList<>();
    private static int currentQuestionIndex = -1;
    private static List<String> selectedAnswers = new ArrayList<>();


    /**
     * Initializes the survey by creating a list of questions and starting the survey.
     */
    private static void startSurvey() {
        String question3 = "What is your view on education policy?";
        String[] options3 = {
            "A. Education policy should prioritize traditional values and national history",
            "B. Education should be inclusive and progressive, promoting diversity and modern values",
            "C. Education should be privatized, allowing for competition and choice",
            "D. Education should be a communal effort with a focus on equal opportunities for all"
        };
        questions.add(new Question(question3, options3));

        String question11 = "How should the government approach housing policy?";
        String[] options11 = {
            "A. Allow the free market to dictate housing policies with minimal government intervention",
            "B. Encourage community-led initiatives and cooperative housing solutions",
            "C. Focus on deregulation to stimulate housing development and bring down costs",
            "D. Promote affordable housing through subsidies and rent controls"
        };
        questions.add(new Question(question11, options11));

        String question13 = "How should the government handle social welfare programs?";
        String[] options13 = {
            "A. Reduce welfare programs and encourage self-reliance",
            "B. Maintain a safety net while encouraging individual responsibility",
            "C. Expand welfare programs to address poverty and inequality",
            "D. Focus on targeted interventions rather than broad welfare programs"
        };
        questions.add(new Question(question13, options13));

        String question1 = "What is your stance on economic inequality?";
        String[] options1 = {
            "A. It should be addressed through reforms that encourage individual responsibility and hard work",
            "B. It should be mitigated through policies that promote equal opportunities for all",
            "C. It should be reduced through progressive taxation and welfare programs",
            "D. It is a natural outcome in a free market, and the government should not intervene"
        };
        questions.add(new Question(question1, options1));

        String question2 = "How should the government approach healthcare?";
        String[] options2 = {
            "A. A system that ensures basic healthcare for all, possibly through a public option alongside private healthcare",
            "B. Universal healthcare funded by the state",
            "C. Private healthcare with minimal government intervention",
            "D. A mixed approach with both private and public options, with reforms to encourage competition"
        };
        questions.add(new Question(question2, options2));

        String question4 = "How should the government handle criminal justice?";
        String[] options4 = {
            "A. Maintain a minimal criminal justice system focused on protecting individual rights",
            "B. Focus on law and order with strict penalties for criminals",
            "C. Focus on rehabilitation and community programs to address the root causes of crime",
            "D. Reform the system to be more just and equitable, focusing on rehabilitation rather than punishment"
        };
        questions.add(new Question(question4, options4));

        String question5 = "What is your stance on environmental policies?";
        String[] options5 = {
            "A. Pursue progressive policies that aim to mitigate climate change and protect the environment",
            "B. Balance environmental protection with economic growth, possibly through market-based solutions",
            "C. Allow the free market to innovate solutions for environmental issues",
            "D. Implement strict regulations and pursue green initiatives to protect the environment"
        };
        questions.add(new Question(question5, options5));

        String question6 = "What is your stance on immigration?";
        String[] options6 = {
            "A. Support pathways to citizenship and comprehensive immigration reform",
            "B. Enforce strict immigration laws and border security",
            "C. Support open borders and welcoming immigrants",
            "D. Balance between secure borders and humane treatment of immigrants"
        };
        questions.add(new Question(question6, options6));

        String question7 = "How should the government handle taxation?";
        String[] options7 = {
            "A. Increase taxes on the wealthy to fund social programs",
            "B. Implement a flat tax system with a consistent rate for all income levels",
            "C. Implement a progressive tax system with higher rates for the wealthy",
            "D. Reduce taxes, especially for corporations and high-income individuals"
        };
        questions.add(new Question(question7, options7));

        String question8 = "What is your view on gun control?";
        String[] options8 = {
            "A. Enforce background checks and restrictions on certain firearms",
            "B. Support strict gun control measures and gun buyback programs",
            "C. Advocate for minimal government intervention in gun ownership",
            "D. Uphold the Second Amendment and oppose additional gun control laws"
        };
        questions.add(new Question(question8, options8));

        String question9 = "How do you perceive the role of the United States in global affairs?";
        String[] options9 = {
            "A. Support diplomacy and cooperation while avoiding unnecessary conflicts",
            "B. Maintain a strong military presence and assertive foreign policy",
            "C. Advocate for a strong international role, diplomacy, and multilateralism",
            "D. Pursue a policy of non-intervention and focus on domestic issues"
        };
        questions.add(new Question(question9, options9));

        String question10 = "What is your stance on workers' rights?";
        String[] options10 = {
            "A. Focus on individual rights and entrepreneurship over collective bargaining",
            "B. Support right-to-work laws and oppose mandatory union membership",
            "C. Advocate for strong unions and workers' protections",
            "D. Encourage a balance between workers' rights and business freedoms"
        };
        questions.add(new Question(question10, options10));

        String question12 = "What is your view on infrastructure development?";
        String[] options12 = {
            "A. Balance infrastructure development with fiscal responsibility",
            "B. Prioritize traditional infrastructure projects like roads and bridges",
            "C. Encourage private sector involvement through public-private partnerships",
            "D. Focus on modernizing infrastructure through green and sustainable projects"
        };
        questions.add(new Question(question12, options12));

        String question14 = "What is your stance on voting rights?";
        String[] options14 = {
            "A. Encourage modernization of the voting system to make it more accessible",
            "B. Support voter ID laws to maintain election integrity",
            "C. Maintain current voting regulations and focus on other issues",
            "D. Advocate for expanded voting rights and oppose voter ID laws"
        };
        questions.add(new Question(question14, options14));

        String question15 = "How do you perceive the role of the government in the economy?";
        String[] options15 = {
            "A. Focus on deregulation and tax cuts to stimulate economic growth",
            "B. Support a mixed economy with both government and private sector involvement",
            "C. Advocate for a laissez-faire approach with minimal government intervention",
            "D. Support government intervention to address economic inequalities"
        };
        questions.add(new Question(question15, options15));

        String question16 = "What is your political affiliation?";
        String[] options16 = {
            "A. Socialist",
            "B. Libertarian",
            "C. Conservative",
            "D. Liberal",
            "E. Other"
        };
        questions.add(new Question(question16, options16));

        // Start the survey by displaying the first question
        showNextQuestion();
    }

    // Label to display the predicted political affiliation
    private static JLabel predictionLabel = new JLabel();

    /**
     * Displays the next question in the survey and updates the prediction based on the answers so far.
     */
    private static void showNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            // Remove previous contents of the main panel
            panel.removeAll();

            // Create a panel for questions and options
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

            // Add empty space (vertical padding) at the top of the question panel
            questionPanel.add(Box.createVerticalStrut(20));

            // Display the next question with its number
            clearQuestion();
            Question question = questions.get(currentQuestionIndex);
            String questionTextWithNumber = "Question " + (currentQuestionIndex + 1) + ": " + question.questionText;
            questionLabel.setText(questionTextWithNumber);
            questionPanel.add(questionLabel);
            for (String option : question.options) {
                JRadioButton radioButton = new JRadioButton(option);
                if (currentQuestionIndex == questions.size() - 1) {
                    radioButton.setActionCommand(option.substring(3));
                } else {
                    radioButton.setActionCommand(String.valueOf(option.charAt(0)));
                }
                optionGroup.add(radioButton);
                questionPanel.add(radioButton);
            }

            // Add a label to display the prediction
            if (currentQuestionIndex > 0) {
                try {
                    Instance newInstance = new DenseInstance(data.numAttributes());
                    newInstance.setDataset(data);

                    for (int i = 0; i < currentQuestionIndex; i++) {
                        newInstance.setValue(i, selectedAnswers.get(i));
                    }

                    for (int i = currentQuestionIndex; i < data.numAttributes() - 1; i++) {
                        newInstance.setMissing(i);
                    }

                    double classIndex = tree.classifyInstance(newInstance);
                    String classValue = data.classAttribute().value((int) classIndex);
                    predictionLabel.setText("Predicted political affiliation based on answers so far: " + classValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                questionPanel.add(Box.createVerticalStrut(20));
                questionPanel.add(predictionLabel);
            }

            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(Box.createVerticalGlue());
            panel.add(questionPanel);
            panel.add(createButtonsPanel());
            panel.add(Box.createVerticalGlue());

            panel.revalidate();
            panel.repaint();
            prevButton.setEnabled(currentQuestionIndex > 0);
        } else {
            handleSurveyCompletion();
        }
    }

    /**
     * Creates a panel containing the Next and Previous buttons.
     *
     * @return the panel containing the buttons
     */
    private static JPanel createButtonsPanel() {
        // Create a panel for buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(prevButton);
        buttonsPanel.add(nextButton);
        return buttonsPanel;
    }

    /**
     * Clears the previous question from the GUI.
     */
    private static void clearQuestion() {
        questionLabel.setText("");
        optionGroup.clearSelection();
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                panel.remove(component);
            }
        }
    }

    /**
     * Handles the Next button click event by saving the selected answer and displaying the next question.
     */
    private static void handleNextButton() {
        ButtonModel selectedOption = optionGroup.getSelection();
        if (selectedOption == null) {
            JOptionPane.showMessageDialog(null, "Please select an option.");
        } else {
            selectedAnswers.add(selectedOption.getActionCommand());
            // Show the next question
            showNextQuestion();
        }
    }

    /**
     * Handles the Previous button click event by going back to the previous question.
     */
    private static void handlePrevButton() {
        // Show the previous question
        currentQuestionIndex -= 2;
        if (currentQuestionIndex >= 0) {
            showNextQuestion();
        }
    }

    /**
     * Handles the completion of the survey by saving the data to a file and making a final prediction.
     */
    private static void handleSurveyCompletion() {
        // Save the survey data to a CSV file
        System.out.println(selectedAnswers);
        saveSurveyDataToFile();
        
        try {
            // Load the ARFF file
            DataSource source = new DataSource("survey_data.arff");
            Instances data = source.getDataSet();
            
            // Set the index of the class attribute (the attribute we want to predict)
            data.setClassIndex(data.numAttributes() - 1);
            
            // Load the trained model
            J48 tree = (J48) SerializationHelper.read("j48model.model");
            
            // Create a new instance for prediction
            Instance newInstance = new DenseInstance(data.numAttributes());
            for (int i = 0; i < selectedAnswers.size() - 1; i++) {
                newInstance.setValue(i, selectedAnswers.get(i).charAt(0));
            }
            newInstance.setDataset(data);
            
            // Make the prediction
            double classIndex = tree.classifyInstance(newInstance);
            String classValue = data.classAttribute().value((int) classIndex);
            JOptionPane.showMessageDialog(null, "Predicted political affiliation: " + classValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JOptionPane.showMessageDialog(null, "Survey completed. Thank you for your responses.");
        System.exit(0);
    }


    /**
     * Saves the survey data to an ARFF file.
     */
    private static void saveSurveyDataToFile() {
        File file = new File("survey_data.arff");
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        
        try {
            // Check if the file exists; if not, create a new file and write the header
            if (!file.exists()) {
                file.createNewFile();
                fileWriter = new FileWriter(file, true);
                printWriter = new PrintWriter(fileWriter);
                
                // Write ARFF header
                printWriter.println("@RELATION political_affiliation");
                printWriter.println();
                for (int i = 1; i <= questions.size()-1; i++) {
                    printWriter.println("@ATTRIBUTE Q" + i + " {A,B,C,D}");
                }            
                printWriter.println("@ATTRIBUTE affiliation {Socialist,Libertarian,Conservative,Liberal,Other}");
                printWriter.println();
                printWriter.println("@DATA");
            } else {
                fileWriter = new FileWriter(file, true);
                printWriter = new PrintWriter(fileWriter);
            }
            
            // Write the survey data to the file
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < selectedAnswers.size(); i++) {
                String answer = selectedAnswers.get(i);
                if (i < selectedAnswers.size() - 1) {
                    // For all questions except the last, get the option letter (A, B, C, or D) and append it to the StringBuilder
                    sb.append(answer.charAt(0)).append(",");
                } else {
                    // For the last question, append the full political affiliation name
                    sb.append(answer);
                }
            }
            printWriter.println(sb.toString());
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
