package com.springai.mediguide.config;

import com.springai.mediguide.rag.MedicalRagService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the medical knowledge base into the vector store on startup.
 */
@Component
public class KnowledgeSeed {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeSeed.class);

    private final VectorStore vectorStore;

    @Value("${mediguide.seed-knowledge:true}")
    private boolean seedEnabled;

    public KnowledgeSeed(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void seed() {
        if (!seedEnabled) {
            log.info("Knowledge seeding disabled via config");
            return;
        }

        List<Document> docs = List.of(
                new Document("""
                    【疾病科普】普通感冒与流感的区别
                    普通感冒症状较轻，主要表现为鼻塞、流涕、咽痛，体温一般不高。
                    流感起病急骤，高热（>38.5℃）、全身酸痛、乏力明显，传染性强。
                    建议：流感高危人群（老人、儿童、孕妇、慢性病患者）
                    在流感季节接种疫苗，发病48小时内使用抗病毒药物效果最佳。
                    """),
                new Document("""
                    【用药安全】抗生素使用原则
                    1. 抗生素仅对细菌感染有效，对病毒性感冒无效。
                    2. 不可自行购买和使用抗生素，需医生处方。
                    3. 遵医嘱完成整个疗程，不可症状好转就停药。
                    4. 滥用抗生素会导致细菌耐药性，是全球公共卫生威胁。
                    """),
                new Document("""
                    【急救知识】发烧的居家处理
                    1. 体温<38.5℃：多饮水、物理降温（温水擦浴）。
                    2. 体温≥38.5℃：可使用退热药（布洛芬或对乙酰氨基酚）。
                    3. 儿童避免使用阿司匹林退热（可能引起Reye综合征）。
                    4. 持续高热超过3天、或伴有意识改变、皮疹、呼吸困难→立即就医。
                    """),
                new Document("""
                    【慢病管理】高血压患者日常注意事项
                    1. 每日测量血压（晨起后1h内、睡前各一次）。
                    2. 低盐饮食（每日<5g盐）。
                    3. 规律服药，不可擅自停药或调整剂量。
                    4. 血压控制目标：一般<140/90，合并糖尿病<130/80。
                    5. 定期复查肾功能、电解质。
                    """),
                new Document("""
                    【疾病科普】糖尿病的血糖监测
                    1. 空腹血糖正常值：3.9-6.1 mmol/L。
                    2. 餐后2小时血糖正常值：<7.8 mmol/L。
                    3. 糖化血红蛋白(HbA1c)反映近3个月平均血糖，目标<7%。
                    4. 血糖监测频率：血糖控制良好者每周2-4次，不稳定者每日多次。
                    """),
                new Document("""
                    【用药安全】常见药物相互作用
                    1. 华法林+阿司匹林：增加出血风险。
                    2. ACEI类降压药+补钾制剂：高血钾风险。
                    3. 他汀类降脂药+大环内酯类抗生素：增加肌肉损伤风险。
                    4. SSRI类抗抑郁药+NSAIDs：增加上消化道出血风险。
                    新开药时务必告知医生当前用药清单。
                    """)
        );

        vectorStore.add(docs);
        log.info("Seeded {} medical knowledge documents", docs.size());
    }
}
