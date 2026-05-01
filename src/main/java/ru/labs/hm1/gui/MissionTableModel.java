package ru.labs.hm1.gui;

import ru.labs.hm1.model.Mission;
import ru.labs.hm1.model.Sorcerer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MissionTableModel extends AbstractTableModel {
    private final String[] columns = {"ID", "Название", "Проклятье", "Статус миссии", "Участники"};
    private final List<Mission> data = new ArrayList<>();

    public void addMission(Mission mission) {
        int row = data.size();
        data.add(mission);
        fireTableRowsInserted(row, row);
    }

    public void setMissions(List<Mission> missions) {
        this.data.clear();
        this.data.addAll(missions);
        fireTableDataChanged();
    }

    public Mission getMission(int row) {
        return data.get(row);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mission mission = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> mission.getId();
            case 1 -> mission.getMissionId();
            case 2 -> (mission.getCurse() != null) ? mission.getCurse().getName() : "Not found";
            case 3 -> mission.getOutcome();
            case 4 -> formatSorcerers(mission.getSorcerers());
            default -> "";
        };
    }

    private String formatSorcerers(List<Sorcerer> sorcerers) {
        if (sorcerers == null || sorcerers.isEmpty()) return "Нет участников";
        return sorcerers.stream().map(Sorcerer::getName).collect(Collectors.joining(", "));
    }
}