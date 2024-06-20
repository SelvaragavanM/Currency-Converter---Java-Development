import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CurrencyConverter extends JFrame {

    private JLabel titleLabel;
    private JLabel amountLabel;
    private JLabel fromLabel;
    private JLabel toLabel;
    private JTextField amountField;
    private JComboBox<String> fromComboBox;
    private JComboBox<String> toComboBox;
    private JButton convertButton;
    private JLabel resultLabel;

    public CurrencyConverter() { 
        initUI();
    }

    private void initUI() {
        setTitle("Currency Converter");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for precise control

        titleLabel = new JLabel("Currency Converter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        titleLabel.setPreferredSize(new Dimension(700, 60));
        add(titleLabel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0); // Add top margin
        gbc.anchor = GridBagConstraints.CENTER;

        amountLabel = new JLabel("Enter Amount");
        amountLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        mainPanel.add(amountLabel, gbc);

        gbc.gridy++;
        fromLabel = new JLabel("Convert From");
        fromLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        mainPanel.add(fromLabel, gbc);

        gbc.gridy++;
        toLabel = new JLabel("Convert To");
        toLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        mainPanel.add(toLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 0); // Add top margin and left padding

        amountField = new JTextField();
        amountField.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        amountField.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(amountField, gbc);

        gbc.gridy++;
        String[] currencies = {"INR", "USD", "EUR", "CAD", "JPY", "CNY"};
        fromComboBox = new JComboBox<>(currencies);
        fromComboBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
        fromComboBox.setBackground(Color.GREEN); // Set background color
        mainPanel.add(fromComboBox, gbc);

        gbc.gridy++;
        toComboBox = new JComboBox<>(currencies);
        toComboBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
        toComboBox.setBackground(Color.ORANGE); // Set background color
        mainPanel.add(toComboBox, gbc);

        gbc.gridy++;
        convertButton = new JButton("CONVERT");
        convertButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
        mainPanel.add(convertButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 10, 10); // Add top margin and horizontal padding

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        resultLabel.setForeground(Color.RED);
        resultLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.add(resultLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void performConversion() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String fromCurrency = (String) fromComboBox.getSelectedItem();
            String toCurrency = (String) toComboBox.getSelectedItem();
            double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
            resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, convertedAmount, toCurrency));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid amount entered");
        }
    }

    private double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double rate = getConversionRate(fromCurrency, toCurrency);
        return amount * rate;
    }

    private double getConversionRate(String fromCurrency, String toCurrency) {
        // Example conversion rates. These would normally be retrieved from an API.
        double usdToInr = 82.743;
        double usdToEur = 0.943;
        double usdToCad = 1.351;
        double usdToJpy = 132.440;
        double usdToCny = 6.871;

        if (fromCurrency.equals("USD")) { 
            switch (toCurrency) {
                case "INR":
                    return usdToInr;
                case "EUR":
                    return usdToEur;
                case "CAD":
                    return usdToCad;
                case "JPY":
                    return usdToJpy;
                case "CNY":
                    return usdToCny;
            }
        } else if (toCurrency.equals("USD")) {
            switch (fromCurrency) {
                case "INR":
                    return 1 / usdToInr;
                case "EUR":
                    return 1 / usdToEur;
                case "CAD":
                    return 1 / usdToCad;
                case "JPY":
                    return 1 / usdToJpy;
                case "CNY":
                    return 1 / usdToCny;
            }
        } else {
            return getConversionRate(fromCurrency, "USD") * getConversionRate("USD", toCurrency);
        }

        return 1.0; // Default case, should not be reached
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CurrencyConverter ex = new CurrencyConverter();
                ex.setVisible(true);
            }
        });
    }
}
