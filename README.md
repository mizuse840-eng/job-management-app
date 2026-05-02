# 👔 就活・インターン管理ダッシュボード

自身の就職活動やインターンシップの選考状況を一元管理し、可視化するためのWebアプリケーションです。

---

## 🚀 デプロイ環境について

現在はインフラ環境の移行に伴い、一時的に公開を停止しています。  
動作の様子は以下のデモ動画をご覧ください。

![デモ動画](demo.gif)

---

## 🎯 開発背景・目的

複数の企業への応募が進む中で、選考フェーズや日程の管理が複雑化したことを課題に感じ、**「自分が必要な情報を一目で把握できるツール」** を目指して開発しました。

既存のツールを利用するだけでなく、自ら課題を解決するためのシステムを構築することで、実務に近い開発経験を積むことを目的としています。

---

## ✨ 主な機能

- **企業管理**: 業界・職種・採用ページURLなど企業の基本情報を一元管理
- **選考履歴管理**: 1社に対して複数の選考ステータスと日付を履歴として記録
- **ステータスサマリー**: 現在の選考状況の分布をリアルタイムで一覧表示
- **企業名検索**: 登録した企業をキーワードで絞り込み表示
- **メモ機能**: 企業ごと・選考ステージごとの詳細メモを記録

---

## 🛠 使用技術

| カテゴリ | 技術 |
| :--- | :--- |
| **バックエンド** | Java 17, Spring Boot, Spring Data JPA, Lombok |
| **フロントエンド** | HTML5, CSS3, Thymeleaf, Bootstrap 5 |
| **データベース** | MySQL |
| **インフラ・ツール** | Docker, Render, Maven, Git, GitHub |

---

## 💡 こだわったポイント

### 1. 汎用マスターテーブル設計によるDB拡張性の確保

初期設計では選考ステータス専用の `statuses` テーブルを持っていました。しかし「希望職種」など別の区分が増えるたびにテーブルを追加する構造では拡張性に乏しいと判断し、**CategoryGroup / Category による汎用マスター設計**へとリファクタリングを行いました。

`group_code` を切り替えるだけで、選考ステータス・希望職種など複数の区分を同一テーブル構造で管理できます。新しい区分はJavaコードを一切修正せず、DBレコードの追加のみで対応可能です。

```java
// Before: ステータス専用テーブル（拡張のたびにテーブルが増える）
@Entity
@Table(name = "statuses")
public class Status {
    @Id
    private String code;
    private String name;
    private int sortOrder;
}
```

```java
// After: 汎用マスター設計（group_codeで区分を切り替える）
@Entity
@Table(name = "category_group")
public class CategoryGroup {
    @Id
    @Column(name = "group_code")
    private String groupCode; // "01"=選考ステータス, "02"=希望職種

    private String groupName;
}

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // サロゲートキーで結合をシンプルに保つ

    @ManyToOne
    @JoinColumn(name = "group_code")
    private CategoryGroup categoryGroup;

    private String categoryCode;
    private String categoryName;
    private int sortOrder;
}
```

### 2. Controller / Service / Repository による責務の分離

「リクエストを受ける」→「ビジネスロジックを実行する」→「DBを操作する」の3層を明確に分離しています。

Controllerはリクエストの受け渡しとViewへのデータセットのみを担当し、バリデーションや集計などのロジックはServiceに集約しています。

```java
// Controller: リクエストを受け取りServiceに委譲するだけ
@PostMapping("/history/add")
public String addHistory(@RequestParam Long companyId,
                         @RequestParam Long statusCategoryId,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eventDate,
                         @RequestParam(required = false) String detailMemo) {
    companyService.addSelectionHistory(companyId, statusCategoryId, eventDate, detailMemo);
    return "redirect:/company/" + companyId;
}
```

```java
// Service: バリデーション・エンティティ生成・保存を一括して担当
public void addSelectionHistory(Long companyId, Long statusCategoryId,
                                LocalDate eventDate, String detailMemo) {
    Company company = companyRepository.findById(companyId).orElseThrow();
    Category status = categoryRepository.findById(statusCategoryId).orElseThrow();

    SelectionHistory history = new SelectionHistory();
    history.setCompany(company);
    history.setStatus(status);
    history.setEventDate(eventDate);
    history.setDetailMemo(detailMemo);
    history.setCreatedAt(LocalDateTime.now());

    selectionHistoryRepository.save(history);
}
```

### 3. 1対多リレーションによる選考履歴の設計

初期設計では1つの企業データに対してステータスを直接持たせていたため、ステータスを更新するたびに過去の状態が上書きされていました。

この問題を解消するため、**企業テーブルと選考履歴テーブルを分離し1対多のリレーションを構築**しました。これにより「いつ・どのステータスで・どんなメモをしたか」という選考の軌跡をすべて保持できます。

```
【旧設計】上書きのため履歴が残らない
company ── status: "1次面接" / date: 2026-05-10

【新設計】すべての断面を保持
company ─┬─ selection_history: ES提出済み  / 2026-04-30 / メモ
         └─ selection_history: 1次面接    / 2026-05-10 / メモ
```

### 4. 自力での問題解決

学習の過程で発生したエラー（Gitの競合・DB接続設定・500エラーなど）に対し、公式ドキュメントやデバッグツールを活用して解決するプロセスを重視しました。

### 5. クラウド環境へのデプロイ

ローカル環境で完結させず、DockerコンテナをRenderにデプロイし、実際の運用環境を意識した構成を経験しました。

---

## 🔄 継続的リファクタリングの記録

開発を進める中で自ら発見した課題を整理し、現役エンジニアとの技術的対話を通じて認識を深めながら、自律的に改善を重ねています。

| # | 内容 |
| :--- | :--- |
| 1 | **Service層の分離**: ControllerからビジネスロジックをServiceに切り離し、責務を明確化 |
| 2 | **UIの改善**: 視認性向上のため、ステータスごとの色分けを実装 |
| 3 | **メモ機能の追加**: 長文対応のDB設計で即日実装 |
| 4 | **ステータス管理のリファクタリング**: EnumからDBマスターテーブル方式へ移行 |
| 5 | **DB設計の全面見直し**: 1対多リレーションの導入・汎用マスターテーブル設計・企業詳細画面の追加 |

---

## 🚀 今後の展望

- 選考通過率などのデータを可視化する分析機能（グラフ化）の追加
- Spring Securityによる認証機能の実装（マルチユーザー対応）
- Webサービスとして再公開し、他の就活生も利用できるアプリへと拡張

---

## 🏃 ローカルでの起動方法

```bash
# リポジトリをクローン
git clone https://github.com/mizuse840-eng/job-management-app.git
cd job-management-app

# Dockerで起動
docker compose up

# ブラウザでアクセス
http://localhost:8080
```

> MySQLの接続設定は `application.properties` を環境に合わせて変更してください。
