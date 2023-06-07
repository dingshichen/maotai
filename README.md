# Maotai

## 环境
- `Project JDK` 设置为 `11`
- `Gradle JDK` 设置为 `17`
- 构建失败下载不到 `sql-parser` 的依赖，需要在 `sql-parser` 项目 `maven clean install` 到本地仓库，这里才能构建
- 如果需要预览 `compose` 组件，可以安装 IDEA [compose](https://plugins.jetbrains.com/plugin/index?xmlId=org.jetbrains.compose.desktop.ide&utm_source=product&utm_medium=link&utm_campaign=IU&utm_content=2023.1) 插件