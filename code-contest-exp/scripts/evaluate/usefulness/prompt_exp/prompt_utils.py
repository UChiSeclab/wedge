from transformers import T5ForConditionalGeneration, AutoTokenizer,GPTNeoForCausalLM,AutoModelForCausalLM,AutoModel, AutoModelForSeq2SeqLM
import torch
from evalplus.provider import make_model
from evalplus.gen.util.openai_request import make_request
import openai
from evalplus.gen.util import openai_request

def construct_prompt_template(inputs, model, tokenizer, num_samples=20):
    try:
        # Set tokenizer's padding token to eos_token for consistency
        tokenizer.pad_token = tokenizer.eos_token

        # Tokenize inputs with padding and convert to model's device
        input_tokens = tokenizer.batch_encode_plus(
            inputs,
            padding=True,
            return_tensors="pt",
        )
        for key in input_tokens:
            if torch.is_tensor(input_tokens[key]):
                input_tokens[key] = input_tokens[key].to(model.device)

        # Generate sequences using the model
        sequences = model.generate(
            **input_tokens,
            max_new_tokens=512,
            do_sample=True,
            num_return_sequences=num_samples
        )

        # Decode generated sequences into text
        generated_texts = tokenizer.batch_decode(sequences, skip_special_tokens=True)

        # Split the outputs for each input, as `num_return_sequences` generates all outputs in a single list
        grouped_texts = []
        for i in range(len(inputs)):
            start_idx = i * num_samples
            end_idx = start_idx + num_samples
            grouped_texts.append(generated_texts[start_idx:end_idx])

        # Post-process to remove input text from generated output
        for i in range(len(grouped_texts)):
            grouped_texts[i] = [
                text.replace(inputs[i], "") if inputs[i] in text else text
                for text in grouped_texts[i]
            ]
    except ValueError as e:
        # Handle specific generation errors
        print(f"ValueError during text generation: {e}")
        grouped_texts = [[] for _ in range(len(inputs))]
    except RuntimeError as e:
        # Handle device-related or memory errors
        print(f"RuntimeError during text generation: {e}")
        grouped_texts = [[] for _ in range(len(inputs))]
    except Exception as e:
        # Catch-all for any other unexpected errors
        print(f"Unexpected error during text generation: {e}")
        grouped_texts = [[] for _ in range(len(inputs))]

    return grouped_texts # List of lists of generated texts for each input

def hf_prompt_one(input, model, tokenizer, num_samples=20):
    # Generate text prompts for a single input
    return construct_prompt_template([input], model, tokenizer, num_samples=num_samples)[0]

def openai_prompt_one(input, client, checkpoint, num_samples=20):
    ret = openai_request.make_auto_request(
        client,
        message=input,
        model=checkpoint,
        max_tokens=1024,
        temperature=1.0,
        n=num_samples,
    )

    outputs = []
    for item in ret.choices:
        outputs.append(item.message.content)

    return outputs

if __name__ == '__main__':
    input = "Please complete the Python function that takes a list of integers as input and returns the sum of all the integers in the list. # Example:\nsum_list([1, 2, 3, 4, 5])\n# Output\n15"
    import openai
    from gpt_caller import API_KEY
    client = openai.OpenAI(
        api_key=API_KEY, base_url=None
    )
    outputs = openai_prompt_one(input, client, "gpt-4o", num_samples=1)
    print(outputs)
