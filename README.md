👔 #就活管理ダッシュボード
自身の就職活動やインターンシップの選考状況を一元管理し、可視化するためのWebアプリケーションです。

🚀 ##デプロイ環境について
現在はインフラ環境の移行に伴い、一時的に公開を停止しています。
動作の様子は以下のデモ動画をご覧ください。
画像を表示

🎯 ##開発背景・目的
複数の企業への応募が進む中で、選考フェーズや締切日の管理が複雑化したことを課題に感じ、「自分が必要な情報を一目で把握できるツール」 を目指して開発しました。
既存のツールを利用するだけでなく、自ら課題を解決するためのシステムを構築することで、実務に近い開発経験を積むことを目的としています。

✨ ##主な機能

応募ステータス管理: 「未着手」「応募済み」「面接待ち」などの状態を一覧で管理
期日管理: 提出期限や面接日程の視認性を向上
メモ機能: 企業ごとの特徴や面接での振り返りを蓄積
企業名検索: 登録した企業をキーワードで絞り込み表示


🛠 ##使用技術
カテゴリ技術バックエンドJava 17, Spring Boot, Spring Data JPA, LombokフロントエンドHTML5, CSS3, Thymeleaf, Bootstrap 5データベースMySQLインフラ・ツールDocker, Render, Maven, Git, GitHub

💡 ##こだわったポイント
1. 拡張性と保守性を意識したDBマスター設計（リファクタリングの実践）
初期段階では、選考ステータス（未着手、面接待ちなど）の管理に Enum を用いてタイポやバグを防ぐ設計としていました。
しかし、実際の就職活動が進むにつれて「一次面接」「最終面接」などステータスが動的に増減することを想定し、データベース上の マスターテーブル（statuses） で管理する設計へとリファクタリングを行いました。
これにより、ソースコードを一切改修することなくデータの追加・変更が可能となり、より実務を想定した変化に強いアーキテクチャを実現しています。
// Before: Enumによるハードコード
public enum JobStatus { UNTOUCHED, APPLIED, WAITING_INTERVIEW }

// After: DBのマスターテーブルで動的管理
@ManyToOne
@JoinColumn(name = "status_id")
private Status status;
2. MVCアーキテクチャに基づく関心の分離
ステータスに応じたバッジの色分けなど、View（HTML）側に記述されがちな複雑な条件分岐を排除。
Controller側でステータスとCSSクラスの Map（辞書）を作成してViewに渡すことで、フロントエンドのコードをスリム化し、保守性を高めています。
java// Controller側でMapを生成してViewに渡す
Map<String, String> statusClassMap = new HashMap<>();
statusClassMap.put("APPLIED", "badge-primary");
statusClassMap.put("INTERVIEW", "badge-warning");
model.addAttribute("statusClassMap", statusClassMap);
html<!-- View側は1行でスッキリ -->
<span th:classappend="${statusClassMap[job.status.code]}">...</span>
3. 自力での問題解決
学習の過程で発生したエラー（Gitの競合・DB接続設定・500エラーなど）に対し、公式ドキュメントやデバッグツールを活用して解決するプロセスを重視しました。
4. クラウド環境へのデプロイ
ローカル環境で完結させず、DockerコンテナをRenderにデプロイし、実際の運用環境を意識した構成を経験しました。

🔄 ##アップデート履歴（エンジニア様からのフィードバック反映）
現役エンジニアの方からいただいたレビューをもとに、継続的な改善を行っています。
#内容1Service層の分離: Controllerからビジネスロジックを切り離し、保守性を向上2UIの改善: 視認性向上のため、ステータスごとの色分けを実装3メモ機能の追加: 長文対応のDB設計で即日実装4ステータス管理のリファクタリング: EnumからDBマスターテーブル方式へ移行

🚀 ##今後の展望

選考通過率などのデータを可視化する分析機能（グラフ化）の追加
Spring Securityによる認証機能の実装（マルチユーザー対応）
Webサービスとして再公開し、他の就活生も利用できるアプリへと拡張


🏃 ##ローカルでの起動方法
bash# リポジトリをクローン
git clone https://github.com/mizuse840-eng/job-management-app.git
cd job-management-app

# Dockerで起動
docker compose up

# ブラウザでアクセス
http://localhost:8080

MySQLの接続設定は application.properties を環境に合わせて変更してください。
