package com.springai.mediguide.tool;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Health data analysis tool for trend tracking and summarization.
 */
public class HealthDataTool {

    public record VitalsSnapshot(LocalDate date, double systolic, double diastolic,
                                  int heartRate, double bloodSugar, double weight) {}

    public record HealthTrend(String metric, String direction, double changePercent,
                               String interpretation, String recommendation) {}

    public static String analyzeVitalsTrend(List<VitalsSnapshot> snapshots) {
        if (snapshots == null || snapshots.size() < 2) {
            return "数据不足以分析趋势，请至少提供2次以上的测量记录。";
        }

        snapshots.sort(Comparator.comparing(VitalsSnapshot::date));
        var first = snapshots.getFirst();
        var last = snapshots.getLast();
        int days = Period.between(first.date(), last.date()).getDays();
        if (days == 0) days = 1;

        List<HealthTrend> trends = new ArrayList<>();

        // Blood pressure trend
        double sbpChange = ((last.systolic() - first.systolic()) / first.systolic()) * 100;
        trends.add(new HealthTrend(
            "收缩压", sbpChange > 0 ? "↑上升" : "↓下降",
            Math.abs(sbpChange),
            sbpChange > 5 ? "血压持续升高，需要注意" :
            sbpChange < -5 ? "血压有所改善" : "血压保持稳定",
            sbpChange > 5 ? "建议减少钠摄入，增加运动，监测血压" : "继续保持"
        ));

        // Blood sugar trend
        double bsChange = ((last.bloodSugar() - first.bloodSugar()) / first.bloodSugar()) * 100;
        trends.add(new HealthTrend(
            "血糖", bsChange > 0 ? "↑上升" : "↓下降",
            Math.abs(bsChange),
            bsChange > 10 ? "血糖控制不佳" :
            bsChange < -10 ? "血糖明显改善" : "血糖相对稳定",
            bsChange > 10 ? "复查糖化血红蛋白(HbA1c)，调整饮食或用药" : "继续当前管理方案"
        ));

        // Weight trend
        double wChange = ((last.weight() - first.weight()) / first.weight()) * 100;
        trends.add(new HealthTrend(
            "体重", wChange > 0 ? "↑上升" : "↓下降",
            Math.abs(wChange),
            wChange > 3 ? "体重明显增加" :
            wChange < -3 ? "体重明显下降" : "体重基本稳定",
            wChange > 5 ? "建议控制饮食，增加活动量" : "继续观察"
        ));

        // Build report
        StringBuilder report = new StringBuilder();
        report.append("📊 **健康趋势分析报告**\n\n");
        report.append("分析周期：").append(first.date()).append(" → ").append(last.date())
              .append("（共").append(days).append("天）\n\n");

        for (HealthTrend t : trends) {
            report.append("- **").append(t.metric()).append("**：")
                  .append(t.direction()).append(" ")
                  .append(String.format("%.1f%%", t.changePercent())).append("\n")
                  .append("  · ").append(t.interpretation()).append("\n")
                  .append("  · 💡 ").append(t.recommendation()).append("\n\n");
        }

        return report.toString();
    }

    public static String generateDailySummary(List<VitalsSnapshot> todayData) {
        if (todayData == null || todayData.isEmpty()) {
            return "今日暂无健康数据。";
        }

        var latest = todayData.getLast();
        return String.format("""
            📋 **今日健康摘要** — %s
            
            - 血压：%.0f/%.0f mmHg
            - 心率：%d bpm
            - 血糖：%.1f mmol/L
            - 体重：%.1f kg
            
            ✅ 建议保持饮水充足，规律作息。
            """,
            latest.date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            latest.systolic(), latest.diastolic(),
            latest.heartRate(), latest.bloodSugar(), latest.weight()
        );
    }
}
