package com.gac.api.presentation.mapper;

import com.gac.api.presentation.dto.response.MovementResponse;
import java.util.List;
import java.util.stream.Collectors;

public final class MovementReportCsvMapper {

    private MovementReportCsvMapper() {
    }

    public static String toCsv(List<MovementResponse> movements) {
        StringBuilder csv = new StringBuilder();
        csv.append(String.join(
                ",",
                "id",
                "type",
                "status",
                "professorRegistrationNumber",
                "attendantId",
                "assetType",
                "assetId",
                "confirmationCode",
                "academicPurpose",
                "room",
                "defectDescription",
                "checkedOutAt",
                "returnedAt",
                "createdAt",
                "loanedAccessories",
                "returnedAccessories"));
        csv.append('\n');

        for (MovementResponse movement : movements) {
            csv.append(String.join(
                    ",",
                    field(movement.id()),
                    field(movement.type()),
                    field(movement.status()),
                    field(movement.professorRegistrationNumber()),
                    field(movement.attendantId()),
                    field(movement.assetType()),
                    field(movement.assetId()),
                    field(movement.confirmationCode()),
                    field(movement.academicPurpose()),
                    field(movement.room()),
                    field(movement.defectDescription()),
                    field(movement.checkedOutAt()),
                    field(movement.returnedAt()),
                    field(movement.createdAt()),
                    field(joinList(movement.loanedAccessories())),
                    field(joinList(movement.returnedAccessories()))));
            csv.append('\n');
        }

        return csv.toString();
    }

    private static String joinList(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        return values.stream().collect(Collectors.joining("|"));
    }

    private static String field(Object value) {
        if (value == null) {
            return "";
        }
        String text = value.toString();
        if (text.contains(",") || text.contains("\"") || text.contains("\n")) {
            return "\"" + text.replace("\"", "\"\"") + "\"";
        }
        return text;
    }
}
