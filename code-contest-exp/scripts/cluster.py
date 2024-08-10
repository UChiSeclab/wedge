"Clusters solution codes"
from typing import List
import torch
from transformers import AutoModel, AutoTokenizer
from sklearn.cluster import KMeans
import numpy as np

from utils import num_tokens_from_string


def code_clustering(
    code_samples: List[str],
    checkpoint: str = "codesage/codesage-large",
    device: str = "cuda",
    n_clusters: int = 5,
):
    """
    Clusters solution codes into the specified number of clusters.

    Args:
    - code_samples (list of str): List of code snippets to be clustered.
    - checkpoint (str): The checkpoint for the model to use.
    - device (str): Device to run the model on ("cuda" or "cpu").
    - n_clusters (int): Number of clusters.

    Returns:
    - list of int: cluster labels for each code sample.
    """

    # Load the model and tokenizer
    tokenizer = AutoTokenizer.from_pretrained(
        checkpoint, trust_remote_code=True, add_eos_token=True
    )
    model = AutoModel.from_pretrained(checkpoint, trust_remote_code=True).to(device)

    # Generate embeddings for each code sample
    embeddings = []
    embedded_code = []
    for code in code_samples:
        if num_tokens_from_string(code) > 1500:
            continue
        inputs = tokenizer.encode(code, return_tensors="pt").to(device)
        embedding = model(inputs)[0]
        with torch.no_grad():
            embedding = embedding.mean(dim=1).squeeze().cpu().numpy()
            embeddings.append(embedding)
        embedded_code.append(code)

    embeddings = np.array(embeddings)
    kmeans = KMeans(n_clusters=n_clusters, random_state=0).fit(embeddings)
    labels = kmeans.labels_.tolist()

    return embedded_code, labels
