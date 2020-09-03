package basic.control.chart;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import basic.common.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;

public class ChartController implements Initializable {
	@FXML private PieChart pieChart;
	@FXML private BarChart barChart;
	@FXML private AreaChart areaChart;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
// 파이차트
// 방법2
		ObservableList<Data> list = FXCollections.observableArrayList(
				new PieChart.Data("AWT", 10), new PieChart.Data("Swing", 30),
				new PieChart.Data("SWT", 25), new PieChart.Data("JavaFX", 35));
// 방법3 ObservableList<Data> list = FXCollections.observableArrayList();
//		list.add(new PieChart.Data("AWT", 10));
//		list.add(new PieChart.Data("Swing", 30));
//		list.add(new PieChart.Data("SWT", 25));
//		list.add(new PieChart.Data("JavaFX", 35));
		pieChart.setData(list);
		
// 바차트	-> 메소드 이용해서 따로빼서 만들어보자
		XYChart.Series<String, Integer> s1 = new XYChart.Series<>();
		s1.setData(getSeries1());
		s1.setName("남자");
		
		XYChart.Series<String, Integer> s2 = new XYChart.Series<>();
		s2.setData(getSeries2());
		s2.setName("여자");
		
		barChart.getData().add(s1);
		barChart.getData().add(s2);
	
// area 차트
		XYChart.Series<String, Integer> s3 = new XYChart.Series<>();
		s3.setData(getSeries3());
		s3.setName("평균온도");
		XYChart.Series<String, Integer> s4 = new XYChart.Series<>();
		s4.setData(getSeries4());
		s4.setName("COVID19");
		
		areaChart.getData().add(s3);
		areaChart.getData().add(s4);
	}
// 남자	
	public ObservableList<XYChart.Data<String, Integer>> getSeries1() {
		ObservableList<XYChart.Data<String, Integer>> list =
				FXCollections.observableArrayList();
		list.add(new XYChart.Data<String, Integer>("2015",70));
		list.add(new XYChart.Data<String, Integer>("2016",40));
		list.add(new XYChart.Data<String, Integer>("2017",50));
		list.add(new XYChart.Data<String, Integer>("2018",30));
		return list;
	}
// 여자	
	public ObservableList<XYChart.Data<String, Integer>> getSeries2() {
		ObservableList<XYChart.Data<String, Integer>> list =
				FXCollections.observableArrayList();
		list.add(new XYChart.Data<String, Integer>("2015",30));
		list.add(new XYChart.Data<String, Integer>("2016",60));
		list.add(new XYChart.Data<String, Integer>("2017",50));
		list.add(new XYChart.Data<String, Integer>("2018",60));
		return list;
	}
// 평균온도
	public ObservableList<XYChart.Data<String, Integer>> getSeries3() {
		Connection conn = ConnectionDB.getDB();
		String sql = "select * from receipt";
		
		ObservableList<XYChart.Data<String, Integer>> list =
				FXCollections.observableArrayList();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new XYChart.Data<>(rs.getString("receipt_month"),rs.getInt("receipt_qty")));
			} // 위에 이거 다 sql디벨로퍼에서 만들어준 테이블의 칼럼 이름
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
// 코로나 감염자
	public ObservableList<XYChart.Data<String, Integer>> getSeries4() {
		ObservableList<XYChart.Data<String, Integer>> list =
				FXCollections.observableArrayList();
		list.add(new XYChart.Data<String, Integer>("09",5));
		list.add(new XYChart.Data<String, Integer>("10",12));
		list.add(new XYChart.Data<String, Integer>("11",15));
		list.add(new XYChart.Data<String, Integer>("12",25));
		return list;
	}
}
