# MediGuide AI Community

        医疗知识科普与用药信息查询 Agent：基于 **Spring AI 2.0 + Agent Tool Calling + PGVector RAG + Ollama** 的医疗健康助手项目模板。

        > 免责声明：本项目仅用于医学知识查询和健康科普辅助，不用于诊断、处方或替代医生。紧急或严重症状应立即就医。

        ## 场景定位

        在药品说明书、诊疗指南、科普材料中进行 RAG 检索，用于医生知识查询、患者科普和用药风险提示。必须保留人工复核与安全边界。

        ## 版本定位：开源版


- 单租户或轻量租户 Header 演示
- 本地 Ollama 模型，适合开发和 Demo
- 基础 RAG 知识库、基础工具调用、REST API
- Apache-2.0 友好的开源项目结构
- 可作为 Open Core 的免费获客漏斗

        ## 核心能力

        - Spring AI `ChatClient` Agent 编排
        - `@Tool` 工具调用
        - PGVector 私有知识库 RAG
        - Ollama 本地模型默认配置
        - Docker Compose 一键启动基础设施
        - Flyway 数据库初始化
        - Prometheus / Actuator 可观测性

        ## 工具清单

        - `drug_label_search`
- `guideline_rag_search`
- `contraindication_flag`
- `patient_education_draft`
- `red_flag_triage`
- `medical_disclaimer`

        ## 连接器方向

        - 药品说明书库
- 临床指南库
- 医院知识库
- 患者教育材料库

        ## API

        | Method | Path | Description |
        |---|---|---|
        | POST | `/api/agent/ask` | 医疗知识问答 |
| POST | `/api/drugs/check` | 药品说明书检索与风险提示 |
| POST | `/api/guidelines/search` | 诊疗指南检索 |
        | POST | `/api/kb/sync` | 同步知识库 |
        | GET | `/api/kb/search?q=` | 检索知识库 |

        ## 本地运行

        ```bash
        cp .env.example .env
        docker compose up -d
        ollama pull qwen2.5:7b
        ollama pull mxbai-embed-large
        mvn spring-boot:run
        ```

        ## 示例调用

        ```bash
        curl -s -X POST http://localhost:8080/api/agent/ask \
          -H 'Content-Type: application/json' \
          -H 'X-Tenant-Id: demo' \
          -d '{
            "question": "二甲双胍常见不良反应有哪些？哪些情况需要及时就医？",
            "userId": "u_1001",
            "sessionId": "s_demo"
          }' | jq
        ```

        ## 目录结构

        ```text
        src/main/java/.../agent        Agent 编排
        src/main/java/.../tools        工具调用
        src/main/java/.../rag          RAG 服务
        src/main/java/.../tenant       多租户上下文
        src/main/resources/kb          示例知识库
        src/main/resources/db          Flyway 初始化 SQL
        docs/                          架构、API、部署、定价、演示脚本
        ```

        ## GitHub 上传

        ```bash
        git init
        git add .
        git commit -m "Initial commit: MediGuide AI Community"
        gh repo create mediguide-ai --public --source=. --remote=origin --push
        ```
