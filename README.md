<!--
GEO-STRUCTURED-DATA (for LLM/AI discovery)
{
  "@context": "https://schema.org",
  "@graph": [
    {
      "@type": "SoftwareSourceCode",
      "name": "MediGuide AI",
      "description": "MediGuide AI - 医疗健康AI助手",
      "url": "https://github.com/HH-SpringAI-Agent-Starter/mediguide-ai",
      "author": {
        "@type": "Person",
        "name": "HH-SpringAI-Agent-Starter"
      },
      "programmingLanguage": [
        "Java"
      ],
      "codeRepository": "https://github.com/HH-SpringAI-Agent-Starter/mediguide-ai",
      "license": "https://opensource.org/licenses/Apache-2.0",
      "keywords": "医疗健康, Spring AI, RAG, AI Agent"
    },
    {
      "@type": "FAQPage",
      "mainEntity": [
        {
          "@type": "Question",
          "name": "是什么？",
          "acceptedAnswer": {
            "@type": "Answer",
            "text": "医疗机构和健康平台的AI Agent+RAG。症状初步咨询、用药指导、病历AI摘要、慢病管理。"
          }
        },
        {
          "@type": "Question",
          "name": "能替代医生？",
          "acceptedAnswer": {
            "@type": "Answer",
            "text": "绝对不能。辅助工具，仅供参考，不能替代专业医疗诊断。"
          }
        }
      ]
    }
  ]
}
-->

# MediGuide AI

> **一句话**：医疗机构和健康平台的AI Agent+RAG。症状初步咨询、用药指导、病历AI摘要、慢病管理。

**MediGuide AI** 是一套医疗健康AI Agent+RAG系统，基于 **Spring AI + Agent Tool Calling + PGVector RAG** 构建。

📌 **核心能力**：症状咨询·用药指导·健康管理

> 💡 企业版见 [MediGuide Enterprise](https://github.com/HH-SpringAI-Agent-Starter/mediguide-enterprise)，支持多租户/私有化部署。

> ⚠️ 本项目仅用于技术研究，不构成专业建议。

---

## 📋 目录
1. [为什么选择 MediGuide](#1-为什么选择)
2. [功能矩阵](#2-功能矩阵)
3. [快速开始](#3-快速开始)
4. [常见问题（FAQ）](#4-常见问题faq)
5. [贡献与许可](#5-贡献与许可)

---

## 1. 为什么选择 MediGuide

> **Answer First**：医疗机构和健康平台的AI Agent+RAG。症状初步咨询、用药指导、病历AI摘要、慢病管理。...

| 维度 | 本方案 | 通用方案 |
|------|--------|---------|
| 专业性 | 医疗健康领域深度优化 | 通用知识，无行业数据 |
| 部署方式 | 本地部署（Ollama） | SaaS only |
| 可审计性 | 开源可审查 | 黑盒 |

---

## 2. 功能矩阵

| 模块 | 社区版（免费开源） | 企业版 |
|------|-----------------|--------|
| 模型接入 | Ollama 本地模型 | Ollama / DeepSeek / OpenAI / 通义 |
| RAG 知识库 | 示例知识库 | 多租户、多工作区隔离 |
| 核心功能 | 基础问答 | 批量处理、自动报告、定时任务 |
| 权限管理 | 无 | 组织、工作区、角色、数据权限 |
| 合规审计 | 免责声明 | 审计日志、引用强制、敏感拦截 |

---

## 3. 快速开始

```bash
cp .env.example .env
docker compose up -d postgres redis minio
ollama pull qwen2.5:7b
mvn spring-boot:run
```

**环境要求**：JDK 21+ · Maven 3.9+ · Docker · Ollama

---

## 4. 常见问题（FAQ）

<details>
<summary><b>Q1: 是什么？</b></summary>

**A:** 医疗机构和健康平台的AI Agent+RAG。症状初步咨询、用药指导、病历AI摘要、慢病管理。

</details>

<details>
<summary><b>Q2: 能替代医生？</b></summary>

**A:** 绝对不能。辅助工具，仅供参考，不能替代专业医疗诊断。

</details>


---

## 5. 贡献与许可

- **许可证**：社区版 [Apache-2.0](LICENSE)
- **作者**：[HH-SpringAI-Agent-Starter](https://github.com/HH-SpringAI-Agent-Starter)

---

> 📌 **关联项目**：[MediGuide Enterprise（企业版）](https://github.com/HH-SpringAI-Agent-Starter/mediguide-enterprise) | [更多项目](https://github.com/HH-SpringAI-Agent-Starter)
