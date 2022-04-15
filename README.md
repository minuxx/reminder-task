# reminder-task

## Dev Environment

- compileSdk 31
- minSdk 21
- targetSdk 30

## Library

- Kotlin
- Coroutine
- Flow
- Navigation
- Room
- ViewModel
- LiveData
- DataBinding
- Hilt
- AndroidX.media
- Glide

## Architecture

MVVM + AAC Clean Architecture

- 하나의 앱 모듈로 구성
- 리마인더와 관련된 기능을 담당하는 feature_reminder 패키지 구성
- feature_ 는 각각 data, domain, presentation layer로 나누고, 의존성 주입을 위해 di 모듈 구성
- data layer
    - SQLite의 데이터를 관장하는 local 패키지 구성
    - AAC 의 RoomDB를 활용해 ReminderDatabase, ReminderDao, ReminderEntity 구성
    - Repository에서 ReminderDao 를 이용해 ReminderEntity를 ReminderDatabase 에서 Get, Insert, Update 하며, 결과(진행상태)를 코루틴의 Flow로 전달
    - Remote 데이터소스가 없으므로 Local 데이터소스만을 참조한다.
    - Repository를 domain layer에서 구현할 경우 ReminderEntity가 domain layer에서 언급되므로, Clean Architecture의 의존성 규칙에 어긋나기 때문에 domain layer에서 Repository Interface만을 설정하고, 실질적인 구현은 data layer에서 한다.
    - Repository에서 Domain Model 의 데이터클래스에 맞게 데이터를 변환해서 Domain Layer (Usecase)로 전달한다. 즉, UI에 보여질 데이터, 비즈니스로직에 쓰일 데이터만 가공해서 전달한다.
- domain layer
    - model 패키지에는 UI에 보여질 데이터클래스 Reminder 를 정의
    - UseCase와 data layer와의 소통을 위해 Repository Interface를 정의
    - UseCase는 하나의 역할만을 수행하게끔 한다. 또한 Repository에서 오는 데이터의 출저가 remote인지 local인지 UseCase는 알 수 없다.
    - UseCase는 presentation layer의 ViewModel에게만 명령을 받아 data layer에서 데이터를 가져오며, 데이터를 요청할 때 ViewModel로부터 전달받은 데이터의 벨리데이션이나 필요에 맞게 가공하는 등 비즈니스로직을 수행할 수 있고, Repository에게 데이터를 요청한다.
- presentation layer
    - main
        - AAC의 Navigation 을 활용해 하나의 MainActivity가 3개의 Fragment를 컨트롤한다.
        - 리마인더 목록(reminders) / 리마인더 설정 (reminder) / 리마인더 알람 (alarm) 에서 공통적으로 필요한 Reminder 데이터와 각 화면에 필요한 UI 상태 데이터를 관장하는 ReminderViewModel 하나만을 설정했다.
        - ReminderViewModel은 domain layer의 UseCase에게 데이터를 요청하고, Coroutine, Flow를 이용해 데이터스트림을 통해 각 상태값을 받을 때마다 UI를 변경하거나 (로딩바 등) View의 이벤트를 호출시킨다.
        - ViewModel이 화면에 특정 결과에 대해 각기 다른 이벤트를 호출하기 위해 core (앱 모듈의 공용 소스 집합) UiEvent sealed class를 만들어 사용했다.
    - reminders
        - 리마인더 목록 화면의 RemindersFragment와 Ui 상태값을 나타내는 RemindersUiState로 구성
        - NavController로 Reminder 설정화면으로 이동할 수 있으며, 생성과 편집 모드로 다르게 진입할 수 있다.
    - reminder
        - 리마인더 설정(상세) 화면의 ReminderFragment와 Ui 상태값을 나타내는 ReminderUiState로 구성
    - alarm
        - 리마인더 알람 화면의 AlarmFragment와 Ui 상태값을 나타내는 AlarmUiState로 구성
        - 특정 시간대에 알림 설정을 Broadcast receive할 AlarmReceiver
        - AlarmReceiver에 의해 실행되어 Notification과 Media를 백그라운드에서도 실행시킬 수 있는 AlarmService
    

## App Module Structure

- manifests
- java/com/minux/reminder
    - core
        - util
    - feature_reminder
        - data
            - local
                - entity
            - repository
        - di
        - domain
            - model
            - repository
            - use_case
        - presentation
            - alarm
            - main
            - reminder
            - reminders
- res
