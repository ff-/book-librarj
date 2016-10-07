(ns book-librarj.subs
    (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :name
 (fn [db]
   (:name db)))

(reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(reg-sub
  :error-text
  (fn [db _]
    (:error-text db)))

(reg-sub
  :books
  (fn [db _]
    (:books db)))
