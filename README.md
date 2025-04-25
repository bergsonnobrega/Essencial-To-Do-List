# Essencial To-Do List 📝

Bem-vindo ao Essencial To-Do List, um aplicativo Android moderno e prático para gerenciamento de tarefas, construído com as tecnologias mais recentes do ecossistema Android Jetpack. Este projeto serve como uma demonstração de habilidades em desenvolvimento Android nativo, focado em boas práticas de arquitetura e UI.

## ✨ Funcionalidades Atuais

*   **Interface Moderna:** UI limpa e intuitiva construída inteiramente com **Jetpack Compose** e seguindo os princípios do **Material Design 3**.
*   **Gerenciamento Completo de Tarefas (CRUD):**
    *   Adicionar novas tarefas com título e descrição opcional.
    *   Visualizar todas as tarefas em uma lista rolável (`LazyColumn`).
    *   Marcar tarefas como concluídas (com feedback visual de linha riscada).
    *   Editar tarefas existentes.
    *   Excluir tarefas individualmente.
*   **Persistência Local:** As tarefas são salvas localmente no dispositivo usando o **Room Persistence Library** (sobre SQLite), garantindo que seus dados não sejam perdidos ao fechar o app.
*   **Arquitetura MVVM:** O projeto segue a arquitetura recomendada **Model-View-ViewModel (MVVM)**, utilizando `ViewModel`, `StateFlow` para comunicação reativa com a UI, e um `Repository` para abstrair a fonte de dados.

## 🛠️ Tecnologias Utilizadas

*   **Linguagem:** Kotlin
*   **UI:** Jetpack Compose (UI Toolkit moderno nativo do Android)
    *   Material Design 3
    *   Navigation Compose
*   **Arquitetura:** MVVM (ViewModel, StateFlow, LiveData)
*   **Persistência:** Room (SQLite ORM)
*   **Assincronia:** Kotlin Coroutines & Flow
*   **Build System:** Gradle (com Kotlin DSL)

## 🚀 Próximos Passos e Melhorias Planejadas

Este projeto está em desenvolvimento ativo! As próximas melhorias incluem:

*   **Datas, Horários e Lembretes:** Permitir que o usuário defina data e hora para as tarefas e receba notificações (`AlarmManager`).
*   **Injeção de Dependência:** Refatorar para utilizar **Hilt** para um gerenciamento de dependências mais robusto e testável.
*   **Polimento da UI/UX:** Adicionar feedback visual (Snackbars), animações, estados de carregamento/vazio aprimorados e opções de ordenação/filtragem.
*   **Testes:** Implementar testes unitários e de instrumentação.

## 🖼️ Screenshots (Em breve)

*(Aqui você pode adicionar screenshots ou GIFs do aplicativo em funcionamento)*
