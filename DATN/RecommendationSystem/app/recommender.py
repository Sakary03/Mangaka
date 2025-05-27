from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd

def build_similarity_matrix(df):
    df["combined_text"] = df["genres"].fillna('') + " " + df["description"].fillna('')
    vectorizer = TfidfVectorizer(stop_words='english')
    tfidf_matrix = vectorizer.fit_transform(df["combined_text"])
    similarity = cosine_similarity(tfidf_matrix)
    return similarity

def recommend_manga(df, similarity, input_ids, top_n=10):
    indices = [df[df["id"] == mid].index[0] for mid in input_ids if mid in df["id"].values]
    if not indices:
        return []

    # Tính tổng điểm similarity của các manga đầu vào
    scores = sum(similarity[i] for i in indices)
    scores[indices] = 0  # không recommend lại chính nó

    # Lấy top_n manga có điểm cao nhất
    top_indices = scores.argsort()[-top_n:][::-1]

    # Trả về thông tin + score
    results = []
    for idx in top_indices:
        results.append({
            "id": int(df.iloc[idx]["id"]),
            "title": df.iloc[idx]["title"],
            "genres": df.iloc[idx]["genres"],
            "score": round(float(scores[idx]), 4)  # làm tròn cho đẹp
        })

    return results
