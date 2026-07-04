# 贡献指南

感谢你考虑为 MediGuide AI 做出贡献！

## 行为准则

本项目遵循开源社区的行为准则。请保持尊重、包容、专业的沟通方式。

## 如何贡献

### 报告 Bug

1. 检查 Issue 是否已存在
2. 使用 Bug 报告模板，包含：
   - 环境信息（JDK 版本、OS、Docker 版本）
   - 复现步骤
   - 预期行为和实际行为
   - 日志和截图（如有）

### 提交 Feature Request

1. 先开 Discussion 讨论可行性
2. 明确功能的医疗合规性
3. 提供用例和使用场景

### 代码贡献流程

```bash
# 1. Fork 并 clone
git clone https://github.com/YOUR_USERNAME/mediguide-ai.git
cd mediguide-ai

# 2. 创建功能分支
git checkout -b feat/your-feature-name

# 3. 编码并测试
# 确保：
# - 所有测试通过：mvn test
# - 代码风格一致
# - 医疗相关内容有免责声明

# 4. Commit（遵循 Conventional Commits）
git commit -m "feat: add medication reminder agent"

# 5. Push 并创建 PR
git push origin feat/your-feature-name
```

### PR 要求

- PR 标题清晰描述变更
- 关联相关 Issue
- 包含测试用例
- 更新文档（如有接口变更）
- 通过 CI 检查

## 开发指南

### 新增 Agent

1. 在 `agent/` 包下创建 Agent 类
2. 在 `tool/` 中添加对应的 Tool 类
3. 在 `MedicalAdvisorService` 中注册新 Agent
4. 更新 `controller` 中的路由
5. 更新 `knowledge/agent-profiles.yaml`

### 新增 RAG 知识

1. 在 `KnowledgeSeed.java` 中添加 Document
2. 或通过 API 接口上传文档

### 医疗合规

- 所有输出必须包含免责声明
- 不提供诊断、不推荐处方药
- 分诊逻辑需符合临床指南
- 敏感信息处理需符合 HIPAA/个保法

## 版本规范

遵循 [SemVer](https://semver.org/)：
- MAJOR：不兼容的 API 变更
- MINOR：向下兼容的新功能
- PATCH：向下兼容的 Bug 修复
