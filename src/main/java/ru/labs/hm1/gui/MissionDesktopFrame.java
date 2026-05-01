package ru.labs.hm1.gui;

import ru.labs.hm1.model.Mission;
import ru.labs.hm1.model.Sorcerer;
import ru.labs.hm1.service.MissionService;

import javax.swing.*;
import java.awt.*;

public class MissionDesktopFrame extends JFrame {

    private final MissionService missionService;
    private final MissionTableModel tableModel = new MissionTableModel();
    private final JTable missionTable;

    public MissionDesktopFrame(MissionService missionService) {
        this.missionService = missionService;

        setTitle("Архив магических миссий");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 600));
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Реестр миссий магического колледжа", SwingConstants.CENTER);
        add(header, BorderLayout.NORTH);

        missionTable = new JTable(tableModel);
        missionTable.setRowHeight(30);
        missionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(missionTable), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Обновить данные");
        refreshButton.addActionListener(event -> onRefresh());

        JButton loadFileButton = new JButton("Загрузить миссию из файла");
        loadFileButton.addActionListener(event -> onLoadFile());

        JButton reportButton = new JButton("Сформировать отчет");
        reportButton.addActionListener(e -> onCreateOutput());

        JButton editButton = new JButton("Редактировать");
        editButton.addActionListener(e -> onEditMission());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        bottomPanel.add(loadFileButton);
        bottomPanel.add(reportButton);
        bottomPanel.add(editButton);
        add(bottomPanel, BorderLayout.SOUTH);

        onRefresh();
    }

    private void onCreateOutput() {
        int selectedRow = missionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, выберите миссию в таблице.");
            return;
        }

        JRadioButton smallBtn = new JRadioButton("Маленький (Только база)", true);
        JRadioButton mediumBtn = new JRadioButton("Средний (База + Маги)");
        JRadioButton largeBtn = new JRadioButton("Большой (Полный отчет)");

        ButtonGroup group = new ButtonGroup();
        group.add(smallBtn);
        group.add(mediumBtn);
        group.add(largeBtn);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Выберите тип отчета:"));
        panel.add(smallBtn);
        panel.add(mediumBtn);
        panel.add(largeBtn);

        int result = JOptionPane.showConfirmDialog(this, panel, "Параметры отчета", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Mission selectedMission = tableModel.getMission(selectedRow);
            Integer id = selectedMission.getId();

            String type = "small";
            if (mediumBtn.isSelected()){
                type = "medium";
            }
            if (largeBtn.isSelected()){
                type = "large";
            }

            try {
                String fullReport = missionService.getReport(id, type);
                JTextArea textArea = new JTextArea(25, 60);
                textArea.setText(fullReport);
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

                JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Отчет: " + type.toUpperCase(), JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage());
            }
        }
    }

    private void onRefresh() {
        tableModel.setMissions(missionService.getAllMissions());
    }

    private void onLoadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл с данными миссии");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                missionService.loadMissionFromFile(filePath);
                onRefresh();
                JOptionPane.showMessageDialog(this, "Миссия успешно загружена и сохранена в базу!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ошибка загрузки: " + e.getMessage());
            }
        }
    }

    private void onEditMission() {
        int selectedRow = missionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите миссию для редактирования.");
            return;
        }

        Mission selectedMission = tableModel.getMission(selectedRow);

        String currentCurse = (selectedMission.getCurse() != null) ? selectedMission.getCurse().getName() : "";
        String currentSorcerers = selectedMission.getSorcerers().stream().map(Sorcerer::getName).collect(java.util.stream.Collectors.joining(", "));

        JTextField curseField = new JTextField(currentCurse);
        JTextField sorcerersField = new JTextField(currentSorcerers);
        JComboBox<String> outcomeBox = new JComboBox<>(new String[]{"SUCCESS", "FAILURE", "PARTIAL_SUCCESS", "UNKNOWN"});
        outcomeBox.setSelectedItem(selectedMission.getOutcome().toString());

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Название проклятья:"));
        panel.add(curseField);
        panel.add(new JLabel("Участники (через запятую):"));
        panel.add(sorcerersField);
        panel.add(new JLabel("Статус миссии:"));
        panel.add(outcomeBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Редактирование миссии #" + selectedMission.getId(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String curseName = curseField.getText();
                String sorcerers = sorcerersField.getText();
                String outcome = (String) outcomeBox.getSelectedItem();

                missionService.updateMission(selectedMission.getId(), curseName, sorcerers, outcome);

                onRefresh();
                JOptionPane.showMessageDialog(this, "Миссия успешно обновлена!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ошибка обновления: " + e.getMessage());
            }
        }
    }
}