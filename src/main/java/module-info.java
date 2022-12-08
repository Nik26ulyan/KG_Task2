module com.company.kg_task2_8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.vsu.cs.uliyanov_n_s.kg_task2 to javafx.fxml;
    exports ru.vsu.cs.uliyanov_n_s.kg_task2;
}