(defproject book-librarj "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [reagent "0.6.0"]
                 [binaryage/devtools "0.8.2"]
                 [re-frame "0.8.0"]
                 [org.clojure/core.async "0.2.391"]
                 [re-com "0.8.3"]
                 [secretary "1.2.3"]
                 [compojure "1.5.0"]
                 [org.danielsz/system "0.3.1"]
                 [yogthos/config "0.8"]
                 [ring/ring-defaults "0.2.1"]
                 [ring-middleware-format "0.7.0"]
                 [org.clojure/java.jdbc "0.6.2-alpha3"]
                 [org.postgresql/postgresql "9.4.1211"]
                 [cljs-ajax "0.5.8"]
                 [day8.re-frame/http-fx "0.0.4"]
                 [http-kit "2.2.0"]
                 [environ "1.1.0"]
                 [hiccup "1.0.5"]
                 [ring "1.4.0"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-environ "1.1.0"]
            [juxt/lein-dockerstalk "0.1.0"]
            [lein-zip "0.1.1"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:env {:db-name "booklibrarj"
          :db-host "127.0.0.1"
          :db-port "5432"
          :db-user "testuser"
          :db-pass "testpass"}
    :plugins      [[lein-figwheel "0.5.8"]]
    :dependencies [[figwheel-sidecar "0.5.8"]]
    :source-paths ["src" "dev"]}
   :prod
   {:env {}}} ;; Look into the 'crendentials.sh' file

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "book-librarj.core/mount-root"}
     :compiler     {:main                 book-librarj.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :jar true
     :compiler     {:main            book-librarj.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]}

  :zip ["Dockerfile"
        "Dockerrun.aws.json"
        "project.clj"
        ".ebextensions"
        "src"
        "resources"]

  :aws {:access-key ~(System/getenv "AWS_ACCESS_KEY")
        :secret-key ~(System/getenv "AWS_SECRET_KEY")
        :beanstalk {:environments [{:name "production"
                                    :cname-prefix "book-librarj-1"
                                    :env {"DB_NAME" ~(System/getenv "DB_NAME")
                                          "DB_HOST" ~(System/getenv "DB_HOST")
                                          "DB_PORT" ~(System/getenv "DB_PORT")
                                          "DB_USER" ~(System/getenv "DB_USER")
                                          "DB_PASS" ~(System/getenv "DB_PASS")}}]
                    :s3-bucket "book-librarj-builds"
                    :stack-name "64bit Amazon Linux 2016.03 v2.1.6 running Docker 1.11.2"
                    :region "eu-west-1"}}

  :main ^:skip-aot book-librarj.core
  :uberjar-name "book-librarj.jar")
