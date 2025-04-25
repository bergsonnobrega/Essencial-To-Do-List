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
*   **Arquitetura MVVM:** O projeto segue a arquitetura recomendada Model-View-ViewModel (MVVM), utilizando `ViewModel` para separar a lógica de UI dos dados e `StateFlow` / `LiveData` (verificar qual está em uso) para observação de dados reativa.

## 🚀 Como Executar

1.  Clone o repositório:
    ```bash
    git clone https://github.com/bergsonnobrega/Essencial-To-Do-List.git
    ```
2.  Abra o projeto no Android Studio (versão recomendada: [Inserir versão, ex: Hedgehog ou mais recente]).
3.  Aguarde a sincronização do Gradle.
4.  Execute o aplicativo em um emulador ou dispositivo físico.

## 🛠️ Tecnologias Utilizadas

*   [Kotlin](https://kotlinlang.org/)
*   [Jetpack Compose](https://developer.android.com/jetpack/compose) (para UI)
*   [Material Design 3](https://m3.material.io/)
*   [Room Persistence Library](https://developer.android.com/training/data-storage/room) (para banco de dados local)
*   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) (componente de arquitetura)
*   [Kotlin Coroutines & Flow](https://developer.android.com/kotlin/flow) (para programação assíncrona)
*   [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) ou [Koin](https://insert-koin.io/) (para injeção de dependência - *se estiver usando*)
*   [Navegação Compose](https://developer.android.com/jetpack/compose/navigation) (para navegação entre telas)

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
