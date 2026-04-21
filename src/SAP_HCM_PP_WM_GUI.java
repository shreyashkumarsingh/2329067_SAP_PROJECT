import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;

public class SAP_HCM_PP_WM_GUI {

    static HashMap<String, Integer> inventory = new HashMap<>();
    static ArrayList<String[]> workforce = new ArrayList<>();
    static ArrayList<String[]> productionLog = new ArrayList<>();

    public static void main(String[] args) {

        JFrame frame = new JFrame("SAP Easy Access - HCM | PP | WM");
        frame.setSize(750, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== MENU =====
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("SAP Modules");
        menu.add(new JMenuItem("HCM - Human Resource"));
        menu.add(new JMenuItem("PP - Production Planning"));
        menu.add(new JMenuItem("WM - Warehouse Management"));
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        JTabbedPane tabs = new JTabbedPane();

        // ================= HCM TAB =================
        JPanel hcmPanel = new JPanel(new GridLayout(5,2,10,10));

        JTextField empName = new JTextField();
        JTextField empRole = new JTextField();

        JLabel hcmOutput = new JLabel("");

        JButton addEmpBtn = new JButton("Add Employee");

        addEmpBtn.addActionListener(e -> {
            String name = empName.getText();
            String role = empRole.getText();

            if(name.isEmpty() || role.isEmpty()){
                hcmOutput.setText("Enter valid details!");
                return;
            }

            workforce.add(new String[]{name, role});
            hcmOutput.setText("Employee Added: " + name);
        });

        hcmPanel.add(new JLabel("Employee Name"));
        hcmPanel.add(empName);
        hcmPanel.add(new JLabel("Role"));
        hcmPanel.add(empRole);
        hcmPanel.add(addEmpBtn);
        hcmPanel.add(hcmOutput);

        tabs.add("HCM", hcmPanel);

        // ================= PP TAB =================
        JPanel ppPanel = new JPanel(new GridLayout(5,2,10,10));

        JTextField productField = new JTextField();
        JTextField qtyField = new JTextField();

        JLabel ppOutput = new JLabel("");

        JButton produceBtn = new JButton("Start Production");

        produceBtn.addActionListener(e -> {
            try {
                String product = productField.getText();
                int qty = Integer.parseInt(qtyField.getText());

                if(product.isEmpty()){
                    ppOutput.setText("Enter product!");
                    return;
                }

                productionLog.add(new String[]{product, String.valueOf(qty)});
                inventory.put(product, inventory.getOrDefault(product,0)+qty);

                ppOutput.setText("Produced " + qty + " units of " + product);

            } catch(Exception ex){
                ppOutput.setText("Invalid Input!");
            }
        });

        ppPanel.add(new JLabel("Product Name"));
        ppPanel.add(productField);
        ppPanel.add(new JLabel("Quantity"));
        ppPanel.add(qtyField);
        ppPanel.add(produceBtn);
        ppPanel.add(ppOutput);

        tabs.add("PP", ppPanel);

        // ================= WM TAB =================
        JPanel wmPanel = new JPanel(new BorderLayout());

        String[] columns = {"Product", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columns,0);
        JTable table = new JTable(model);

        JButton refreshBtn = new JButton("Refresh Inventory");

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            for(String key : inventory.keySet()){
                model.addRow(new Object[]{key, inventory.get(key)});
            }
        });

        wmPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        wmPanel.add(refreshBtn, BorderLayout.SOUTH);

        tabs.add("WM", wmPanel);

        // ===== FINAL ADD =====
        frame.add(tabs);
        frame.setVisible(true);
    }
}