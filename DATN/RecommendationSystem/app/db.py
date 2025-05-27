import os
import psycopg2
import pandas as pd

def fetch_all_manga():
    conn = psycopg2.connect(
        host=os.getenv("DB_HOST", "localhost"),
        port=int(os.getenv("DB_PORT", 5432)),
        database=os.getenv("DB_NAME", "mangaka_db"),
        user=os.getenv("DB_USER", "postgres"),
        password=os.getenv("DB_PASS", "123456")
    )
    query = """
    SELECT m.id, m.title, m.description, m.overview, 
           STRING_AGG(mg.genre, ',') AS genres
    FROM manga m
    LEFT JOIN manga_genres mg ON m.id = mg.manga_id
    GROUP BY m.id
    """
    df = pd.read_sql_query(query, conn)
    conn.close()
    return df
