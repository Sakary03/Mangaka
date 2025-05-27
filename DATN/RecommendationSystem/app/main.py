from fastapi import FastAPI, Query
from fastapi.middleware.cors import CORSMiddleware
from app.db import fetch_all_manga
from app.recommender import build_similarity_matrix, recommend_manga

app = FastAPI()

# Add CORS middleware to allow requests from all origins
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allows all origins
    allow_credentials=True,
    allow_methods=["*"],  # Allows all methods
    allow_headers=["*"],  # Allows all headers
)

manga_df = fetch_all_manga()
similarity_matrix = build_similarity_matrix(manga_df)

@app.get("/recommend")
def get_recommendations(
    ids: list[int] = Query(...),
    top_n: int = 5 
):
    result = recommend_manga(manga_df, similarity_matrix, ids, top_n=top_n)
    return {"recommendations": result}

