# MediGuide AI

> **一句话**：医疗机构和健康平台的AI Agent+RAG系统 — 症状咨询、用药指导、病历摘要、慢病管理。

**MediGuide AI** 是一套**多智能体医疗健康系统**，基于 **Spring AI + Tool Calling + PGVector RAG** 构建，
覆盖医疗咨询的四大核心场景：症状分诊、用药指导、健康摘要、慢病管理。

> 💡 **社区版**完全免费开源（Apache-2.0） | 企业版支持多租户/私有化部署/合规审计

> ⚠️ **免责声明**：本项目仅用于技术研究和教育参考，**不构成医疗建议**。如有紧急情况请立即就医。

---

## 📖 目录

- [架构](#架构)
- [功能矩阵](#功能矩阵)
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [API 文档](#api-文档)
- [技术栈](#技术栈)
- [Roadmap](#roadmap)
- [贡献与许可](#贡献与许可)

---

## 🏗️ 架构

```
┌──────────────────────────────────────────┐
│              Client Layer                │
│       REST API (Web / Mobile)           │
└────────────────┬─────────────────────────┘
                 │
┌────────────────▼─────────────────────────┐
│    MedicalAdvisorService (路由编排层)     │
│  ┌──────┬──────┬──────┬──────┐           │
│  │分诊    │用药    │摘要    │慢病    │           │
│  │Agent  │Agent  │Agent  │Agent  │           │
│  └──┬───┴──┬───┴──┬───┴──┬───┘           │
│     │      │      │      │                 │
│  ┌──▼──┐┌──▼──┐┌──▼──┐┌──▼──┐            │
│  │分诊   ││用药   ││健康   ││慢病   │            │
│  │Tool  ││Tool  ││Tool  ││Tool  │            │
│  └─────┘└─────┘└─────┘└─────┘            │
└────────────────┬─────────────────────────┘
                 │
┌────────────────▼─────────────────────────┐
│          RAG (PGVector 向量检索)          │
│      Embedding → 语义搜索 → 知识增强      │
└──────────────────────────────────────────┘
```

### Agent 协作流程

```
用户输入 → 路由层判定类别
  ├─ SYMPTOM     → 紧急分诊 → 三级分诊等级(URGENT/CONSULT/SELF_CARE)
  ├─ MEDICATION  → 药品查询 → 相互作用检查 → 用药指导
  ├─ HEALTH_SUMMARY → 数据分析 → 趋势报告 → 综合摘要
  └─ CHRONIC_CARE   → 慢病指南 → 管理清单 → 生活建议
        ↓
  统一响应(含分诊等级 + 参考来源 + 免责声明)
```

---

## 🎯 功能矩阵

| 模块 | 社区版（免费开源） | 企业版 |
|------|-------------------|--------|
| 模型接入 | Ollama 本地模型 | Ollama / DeepSeek / OpenAI / 通义 |
| RAG 知识库 | 示例知识库（启动自动注入） | 多租户、多工作区隔离 |
| **症状分诊** | ✅ 三级分诊 + 红色警报识别 | ✅ + 自定义分诊词库 |
| **用药指导** | ✅ 药品查询 + 相互作用检查 | ✅ + 药品数据库接入 |
| **健康摘要** | ✅ 体征趋势分析 | ✅ + 批量报告、自动推送 |
| **慢病管理** | ✅ 糖尿病/高血压/COPD/哮喘 | ✅ + 更多病种、AI 个性化方案 |
| 权限管理 | ❌ | 组织、工作区、角色、数据权限 |
| 合规审计 | 免责声明 | 审计日志、强制引用、敏感拦截 |

---

## 🚀 快速开始

### 环境要求

- JDK 21+
- Maven 3.9+
- Docker & Docker Compose
- Ollama（本地 LLM）

### 1. 启动基础设施

```bash
cd docker
docker compose up -d
```

### 2. 拉取模型

```bash
ollama pull qwen2.5:7b       # 对话模型
ollama pull bge-m3:latest    # 嵌入模型
```

### 3. 启动应用

```bash
mvn spring-boot:run
```

### 4. 测试

```bash
# 健康检查
curl http://localhost:8083/api/mediguide/health

# 症状咨询
curl -X POST http://localhost:8083/api/mediguide/chat \
  -H "Content-Type: application/json" \
  -d '{"symptom":"头痛发热三天，体温38度","age":30,"category":"SYMPTOM"}'

# 用药查询
curl -X POST http://localhost:8083/api/mediguide/chat \
  -H "Content-Type: application/json" \
  -d '{"currentMedications":["warfarin","aspirin"],"category":"MEDICATION"}'
```

---

## 📁 项目结构

```
mediguide-ai/
├── pom.xml                        # Maven 构建
├── README.md                      # 项目文档
├── CHANGELOG.md                   # 变更日志
├── CONTRIBUTING.md                # 贡献指南
├── requirements.md                # 需求规格说明书
├── .gitignore
├── docker/
│   ├── docker-compose.yml         # PostgreSQL(PGVector)+Redis+Ollama
│   └── Dockerfile                 # 应用镜像
└── src/
    ├── main/
    │   ├── java/com/springai/mediguide/
    │   │   ├── MediGuideApplication.java
    │   │   ├── agent/
    │   │   │   ├── SymptomTriageAgent.java      # 症状分诊 Agent
    │   │   │   ├── MedicationGuideAgent.java     # 用药指导 Agent
    │   │   │   ├── HealthSummaryAgent.java       # 健康摘要 Agent
    │   │   │   └── ChronicCareAgent.java         # 慢病管理 Agent
    │   │   ├── config/
    │   │   │   ├── AppConfig.java
    │   │   │   ├── VectorStoreConfig.java
    │   │   │   └── KnowledgeSeed.java           # 知识种子
    │   │   ├── controller/
    │   │   │   └── MedicalChatController.java
    │   │   ├── model/
    │   │   │   ├── MedicalQuery.java
    │   │   │   └── MedicalResponse.java
    │   │   ├── rag/
    │   │   │   └── MedicalRagService.java       # RAG 检索
    │   │   ├── service/
    │   │   │   └── MedicalAdvisorService.java   # 路由编排
    │   │   └── tool/
    │   │       ├── DrugInteractionTool.java     # 药物相互作用
    │   │       ├── EmergencyTriageTool.java     # 紧急分诊
    │   │       ├── HealthDataTool.java          # 健康数据分析
    │   │       └── ChronicCareTool.java         # 慢病管理工具
    │   └── resources/
    │       ├── application.yml
    │       ├── application.properties
    │       ├── db/migration/V1__init_health_records.sql
    │       └── knowledge/agent-profiles.yaml
    └── test/java/com/springai/mediguide/
        └── MediGuideApplicationTests.java
```

---

## 📡 API 文档

### POST /api/mediguide/chat

**请求体**：

```json
{
  "symptom": "头痛发热三天，体温38度",
  "age": 30,
  "gender": "男",
  "duration": "3天",
  "preExistingConditions": ["高血压"],
  "currentMedications": ["硝苯地平"],
  "query": "需要去医院吗",
  "category": "SYMPTOM"
}
```

**响应**：

```json
{
  "sessionId": "uuid",
  "answer": "...（分诊结果 + 建议 + 免责声明）",
  "triageLevel": "CONSULT",
  "disclaimer": "⚠️ 此信息仅供参考...",
  "recommendedActions": ["🏥 建议24h内就医", "📋 记录症状变化"],
  "agentSource": "SymptomTriage"
}
```

**Category 取值**：`SYMPTOM` / `MEDICATION` / `HEALTH_SUMMARY` / `CHRONIC_CARE`

### GET /api/mediguide/health

服务健康检查。

### GET /api/mediguide/triage/levels

查看分诊等级说明。

---

## 💻 技术栈

| 类别 | 技术 |
|------|------|
| **框架** | Spring Boot 3.4, Spring AI 1.0.4 |
| **AI 模型** | Ollama (Qwen2.5), 可选 OpenAI |
| **向量检索** | PGVector + bge-m3 Embedding |
| **数据库** | PostgreSQL 17 |
| **缓存** | Redis 7 |
| **构建** | Maven 3.9+, JDK 21 |
| **容器化** | Docker Compose |

---

## 🗺️ Roadmap

- [x] 四 Agent 基础架构（症状/用药/摘要/慢病）
- [x] PGVector RAG 知识库
- [ ] 企业版多租户隔离
- [ ] 语音问诊（STT + TTS）
- [ ] 医疗影像 AI 接入（X光/CT）
- [ ] 电子病历结构化解析
- [ ] 用药提醒 + 复诊提醒推送
- [ ] HIPAA 合规审计日志

---

## 🤝 贡献与许可

- **许可证**：[Apache-2.0](LICENSE)
- **作者**：[HH-SpringAI-Agent-Starter](https://github.com/HH-SpringAI-Agent-Starter)
- **贡献指南**：见 [CONTRIBUTING.md](CONTRIBUTING.md)

---

> 🔗 **关联项目**：[更多 Spring AI Agent 项目](https://github.com/HH-SpringAI-Agent-Starter)
