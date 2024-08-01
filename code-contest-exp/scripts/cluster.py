import torch
from transformers import AutoModel, AutoTokenizer
from sklearn.cluster import KMeans
import numpy as np
import tiktoken

def num_tokens_from_string(string: str, encoding_name="cl100k_base") -> int:
    encoding = tiktoken.get_encoding(encoding_name)
    num_tokens = len(encoding.encode(string))
    return num_tokens

def cluster_code_snippets(code_samples, checkpoint="codesage/codesage-large", device="cuda", n_clusters=5):
    """
    Clusters code samples into the specified number of clusters.

    Args:
    - code_samples (list of str): List of code snippets to be clustered.
    - checkpoint (str): The checkpoint for the model to use.
    - device (str): Device to run the model on ("cuda" or "cpu").
    - n_clusters (int): Number of clusters.

    Returns:
    - list of int: Cluster labels for each code sample.
    """

    # Load the model and tokenizer
    tokenizer = AutoTokenizer.from_pretrained(checkpoint, trust_remote_code=True, add_eos_token=True)
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
            # Average the embeddings across the sequence length dimension
            embedding = embedding.mean(dim=1).squeeze().cpu().numpy()
            embeddings.append(embedding)
        embedded_code.append(code)

    # Convert embeddings list to numpy array
    embeddings = np.array(embeddings)

    # Perform K-means clustering
    kmeans = KMeans(n_clusters=n_clusters, random_state=0).fit(embeddings)

    # Get the cluster labels
    labels = kmeans.labels_.tolist()

    return embedded_code, labels