from transformers import T5ForConditionalGeneration, AutoTokenizer,GPTNeoForCausalLM,AutoModelForCausalLM,AutoModel, AutoModelForSeq2SeqLM
import torch
import openai
from evalplus.gen.util import openai_request

@DeprecationWarning
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
            max_new_tokens=4096,
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

def hf_prompt_one(input, model, tokenizer, num_samples=1):
    # Generate text prompts for a single input
    inputs = tokenizer(input, return_tensors="pt")
    input_ids = inputs.input_ids.to(model.device)
    with torch.no_grad():
        outputs = model.generate(
            input_ids,
            max_length=4096,
            num_return_sequences=num_samples,
            do_sample=True,
            temperature=0.8,
            pad_token_id=tokenizer.eos_token_id,
            attention_mask=inputs.attention_mask.to(model.device)
        )

    generated_texts = [
        tokenizer.decode(output, skip_special_tokens=True) for output in outputs
    ]

    return [text[len(input):].strip() if text.startswith(input) else text for text in generated_texts]

def openai_prompt_one(input, client, checkpoint, num_samples=1):
    ret = openai_request.make_auto_request(
        client,
        message=input,
        model=checkpoint,
        max_tokens=4096,
        temperature=0.8,
        n=num_samples,
    )

    outputs = []
    for item in ret.choices:
        outputs.append(item.message.content)

    return outputs

if __name__ == '__main__':
    input = "Please complete the Python function that takes a list of integers as input and returns the sum of all the integers in the list. # Example:\nsum_list([1, 2, 3, 4, 5])\n# Output\n15"
    import openai
    from gpt_caller import OPENAI_API_KEY
    client = openai.OpenAI(
        api_key=OPENAI_API_KEY, base_url=None
    )
    outputs = openai_prompt_one(input, client, "gpt-4o", num_samples=1)
    print(outputs)
