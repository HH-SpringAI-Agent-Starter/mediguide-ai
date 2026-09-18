# Security Policy

## Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| 0.0.x   | :white_check_mark: |

## Reporting a Vulnerability

MediGuide AI is a medical-health demonstration project. The safety of health-related
data and AI output is our top priority.

If you discover a security vulnerability, please **do NOT** open a public issue.
Instead, report it privately to the maintainers:

* Email: [github@hh-springai-agent-starter.dev](mailto:github@hh-springai-agent-starter.dev)
* Subject prefix: `[SECURITY] mediguide-ai`

### What to include

* Affected version(s) and environment (JDK, Docker, deployment mode)
* Vulnerability type and severity estimate
* Steps to reproduce (minimal, sanitized)
* Any suggested fix (optional)

### Response commitment

* **Acknowledgment**: within 48 hours
* **Initial assessment**: within 5 business days
* **Fix / mitigation plan**: as soon as a root cause is confirmed

We will keep you informed of the progress and coordinate disclosure timing.

## Security & Compliance Notes

* This project is for **technical research and education only** and does not
  constitute medical advice.
* AI responses must always include the disclaimer and must never provide a
  definitive diagnosis.
* Health data is transmitted and stored encrypted; data minimization applies.
* Do not commit real patient data, API keys, or Ollama/DB credentials to the
  repository. Use environment variables or `.env` (git-ignored).
* Suspected prompt-injection or harmful medical advice output should be reported
  through the channel above.

## Responsible Disclosure

We follow a coordinated disclosure model. Please allow maintainers a reasonable
window (default 90 days) before public disclosure.